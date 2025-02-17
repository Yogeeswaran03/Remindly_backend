package com.example.hivemindbackend.service;

import com.example.hivemindbackend.model.User;
import com.example.hivemindbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemplateService templateService;  // Inject TemplateService

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(User user) throws Exception {
        // Check if username or email already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Username or Email already exists");
        }

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Create default templates for the new user
        templateService.createDefaultTemplates(savedUser);

        return savedUser;
    }

    public User authenticate(String email, String password) {
        // Find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Compare the provided password with the stored hashed password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;  // Return the user if authentication is successful
            }
        }
        return null;  // Return null if authentication fails
    }
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
}
