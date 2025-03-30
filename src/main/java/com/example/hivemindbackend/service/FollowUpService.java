package com.example.hivemindbackend.service;

import com.example.hivemindbackend.model.Client;
import com.example.hivemindbackend.model.Template;
import com.example.hivemindbackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FollowUpService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 30 10 * * ?") // Runs every day at 10:30 AM
    public void processFollowUps() {
        LocalDate today = LocalDate.now();
        System.out.println("Follow-up process triggered at: " + today);

        // Process all clients
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            if (client.getUser() != null && !client.isStatus() &&
                    today.isAfter(client.getFollowUpStartDate()) &&
                    today.isBefore(client.getFollowUpTillDate())) {

                int followUpFrequency = client.getFollowUpFrequency();

                // Ensure frequency is not zero to avoid division by zero error
                if (followUpFrequency > 0 &&
                        ChronoUnit.DAYS.between(client.getFollowUpStartDate(), today) % followUpFrequency == 0) {

                    Template template = client.getTemplate();
                    if (template != null) {
                        String subject = template.getTemplateSubject();
                        String content = template.getTemplateContent();

                        // Replace placeholders in the template with actual values
                        String personalizedContent = content
                                .replace("[Client's Name]", client.getName()) // Client Name
                                .replace("[Your Name]", client.getUser().getUsername()); // User Name

                        // Debugging logs
                        System.out.println("--------------------------------------------------");
                        System.out.println("Follow-up Triggered for Client: " + client.getName());
                        System.out.println("Client Email: " + client.getEmail());
                        System.out.println("Email Subject: " + subject);
                        System.out.println("Personalized Email Content: " + personalizedContent);
                        System.out.println("--------------------------------------------------");

                        // Send follow-up email
                        emailService.sendEmail(client.getEmail(), subject, personalizedContent);

                        // Acknowledge sender
                        String acknowledgment = "Follow-up sent to " + client.getName() + " using template: " + template.getTemplateName();
                        System.out.println("Acknowledgment Email Sent to: " + client.getUser().getEmail());
                        System.out.println("Acknowledgment Content: " + acknowledgment);

                        emailService.sendEmail(client.getUser().getEmail(), "Acknowledgment", acknowledgment);
                    }
                } else {
                    System.out.println("Skipping Client: " + client.getName() + " (Follow-up frequency issue)");
                }
            } else {
                System.out.println("Skipping Client: " + client.getName() + " (Invalid Date Range or Status)");
            }
        }
    }


}
