package com.example.hivemindbackend.controller;

import com.example.hivemindbackend.model.Template;
import com.example.hivemindbackend.model.User;
import com.example.hivemindbackend.service.AuthService;
import com.example.hivemindbackend.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private AuthService authService;

    // Fetch all templates for a user by userId
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Template>> getAllTemplatesByUserId(@PathVariable Long userId) {
        User user = authService.getUserById(userId);
        List<Template> templates = templateService.findByUser(user);
        return ResponseEntity.ok(templates);
    }

    // Fetch only default templates for a user by userId
    @GetMapping("/default/{userId}")
    public ResponseEntity<List<Template>> getDefaultTemplatesByUserId(@PathVariable Long userId) {
        User user = authService.getUserById(userId);
        List<Template> defaultTemplates = templateService.findDefaultTemplatesByUser(user);
        return ResponseEntity.ok(defaultTemplates);
    }

    // Fetch only customizable templates for a user by userId
    @GetMapping("/custom/{userId}")
    public ResponseEntity<List<Template>> getCustomTemplatesByUserId(@PathVariable Long userId) {
        User user = authService.getUserById(userId);
        List<Template> customTemplates = templateService.findCustomTemplatesByUser(user);
        return ResponseEntity.ok(customTemplates);
    }

    // Post a new template for a user by userId
    @PostMapping("/new/{userId}")
    public ResponseEntity<Template> createTemplate(@PathVariable Long userId, @RequestBody Template template) {
        User user = authService.getUserById(userId);
        template.setUser(user);
        Template savedTemplate = templateService.saveTemplate(template);
        return ResponseEntity.ok(savedTemplate);
    }

    // Update an existing template by templateId
    @PutMapping("/update/{templateId}")
    public ResponseEntity<Template> updateTemplate(@PathVariable Long templateId, @RequestBody Template updatedTemplate) {
        Template existingTemplate = templateService.getTemplateById(templateId);
        if (existingTemplate != null) {
            existingTemplate.setTemplateName(updatedTemplate.getTemplateName());
            existingTemplate.setTemplateSubject(updatedTemplate.getTemplateSubject());
            existingTemplate.setTemplateContent(updatedTemplate.getTemplateContent());
            Template savedTemplate = templateService.saveTemplate(existingTemplate);
            return ResponseEntity.ok(savedTemplate);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a template by templateId
    @DeleteMapping("/delete/{templateId}")
    public ResponseEntity<String> deleteTemplate(@PathVariable Long templateId) {
        boolean isDeleted = templateService.deleteTemplate(templateId);
        if (isDeleted) {
            return ResponseEntity.ok("Template deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
