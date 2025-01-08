package com.example.hivemindbackend.controller;


import com.example.hivemindbackend.model.Client;
import com.example.hivemindbackend.repository.ClientRepository;
import com.example.hivemindbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}")
    public Client createClient(@PathVariable Long userId, @RequestBody Client client) {
        return userRepository.findById(userId).map(user -> {
            client.setUser(user);
            return clientRepository.save(client);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/user/{userId}")
    public List<Client> getClientsByUser(@PathVariable Long userId) {
        return clientRepository.findByUser_UserIdAndStatusFalse(userId);
    }
    @GetMapping("/completed/{userId}")
    public List<Client> getClientByUser(@PathVariable Long userId) {
        return clientRepository.findByUser_UserIdAndStatusTrue(userId);
    }

    @PutMapping("/user/{userId}/{id}")
    public ResponseEntity<Client> editCard(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody Client updatedClient) {

        // Find the client by ID and user ID
        Optional<Client> optionalClient = clientRepository.findByIdAndUserId(id, userId);

        if (optionalClient.isEmpty()) {
            // Return 404 if the client is not found
            return ResponseEntity.notFound().build();
        }

        Client existingClient = optionalClient.get();

        // Update the fields of the existing client
        existingClient.setName(updatedClient.getName());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setInvoiceLink(updatedClient.getInvoiceLink());
        existingClient.setFollowUpMessage(updatedClient.getFollowUpMessage());
        existingClient.setFollowUpStartDate(updatedClient.getFollowUpStartDate());
        existingClient.setFollowUpFrequency(updatedClient.getFollowUpFrequency());
        existingClient.setFollowUpTillDate(updatedClient.getFollowUpTillDate());
        existingClient.setStatus(updatedClient.isStatus());

        // Save the updated client
        Client savedClient = clientRepository.save(existingClient);

        // Return the updated client in the response
        return ResponseEntity.ok(savedClient);
    }
    @DeleteMapping("/user/{userId}/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long userId, @PathVariable Long id) {
        Optional<Client> optionalClient = clientRepository.findByIdAndUserId(id, userId);
        if (optionalClient.isPresent()) {
            clientRepository.delete(optionalClient.get());
            return ResponseEntity.noContent().build();  // Return 204 No Content
        }
        return ResponseEntity.notFound().build();  // Return 404 if the client is not found
    }



}
