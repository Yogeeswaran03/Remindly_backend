package com.example.hivemindbackend.service;

import com.example.hivemindbackend.model.Client;
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

    @Scheduled(cron = "0 0 10 * * ?") // Every day at 9 AM
    public void processFollowUps() {
        LocalDate today = LocalDate.now();

        // Process all clients
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            if (client.getUser() != null && !client.isStatus() &&
                    today.isAfter(client.getFollowUpStartDate()) &&
                    today.isBefore(client.getFollowUpTillDate()) &&
                    ChronoUnit.DAYS.between(client.getFollowUpStartDate(), today) % client.getFollowUpFrequency() == 0) {

                // Send follow-up email
                emailService.sendEmail(client.getEmail(), "Follow-Up", client.getFollowUpMessage());

                // Acknowledge to the sender
                emailService.sendEmail(client.getUser().getEmail(), "Acknowledgment", "Follow-up sent to " + client.getName());
            }
        }
    }
}

