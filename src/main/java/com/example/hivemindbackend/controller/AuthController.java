package com.example.hivemindbackend.controller;

import com.example.hivemindbackend.model.User;
import com.example.hivemindbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            authService.register(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User loggedInUser = authService.authenticate(user.getEmail(), user.getPassword());

        if (loggedInUser != null) {
            // Optionally, exclude sensitive fields like password before sending the user data
            loggedInUser.setPassword(null);  // Nullify the password field before sending

            return ResponseEntity.ok(loggedInUser);  // Send the user data after successful login
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
