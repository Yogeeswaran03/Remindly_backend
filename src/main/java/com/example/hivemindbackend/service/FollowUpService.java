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

    @Scheduled(cron = "0 0 10 * * ?") // Every day at 10 AM
    public void processFollowUps() {
        LocalDate today = LocalDate.now();

        // Process all clients
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            if (client.getUser() != null && !client.isStatus() &&
                    today.isAfter(client.getFollowUpStartDate()) &&
                    today.isBefore(client.getFollowUpTillDate()) &&
                    ChronoUnit.DAYS.between(client.getFollowUpStartDate(), today) % client.getFollowUpFrequency() == 0) {

                Template template = client.getTemplate();
                if (template != null) {
                    // Use the templateSubject and templateContent from the associated template
                    String subject = template.getTemplateSubject();
                    String content = template.getTemplateContent();

                    // Send follow-up email to the client
                    emailService.sendEmail(client.getEmail(), subject, content);

                    // Acknowledge to the sender
                    String acknowledgment = "Follow-up sent to " + client.getName() + " using template: " + template.getTemplateName();
                    emailService.sendEmail(client.getUser().getEmail(), "Acknowledgment", acknowledgment);
                }
            }
        }
    }
}
