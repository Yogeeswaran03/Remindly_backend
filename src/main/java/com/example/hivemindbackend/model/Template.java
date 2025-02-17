package com.example.hivemindbackend.model;

import jakarta.persistence.*;

@Entity
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String templateName;

    @Column(nullable = false)
    private String templateSubject;

    @Lob
    @Column(nullable = false)
    private String templateContent;

    @Column(nullable = false)
    private boolean isDefaultTemplate;

    // Constructors
    public Template() {}

    public Template(User user, String templateName, String templateSubject, String templateContent, boolean isDefaultTemplate) {
        this.user = user;
        this.templateName = templateName;
        this.templateSubject = templateSubject;
        this.templateContent = templateContent;
        this.isDefaultTemplate = isDefaultTemplate;
    }

    // Getters and Setters
    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateSubject() {
        return templateSubject;
    }

    public void setTemplateSubject(String templateSubject) {
        this.templateSubject = templateSubject;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public boolean isDefaultTemplate() {
        return isDefaultTemplate;
    }

    public void setDefaultTemplate(boolean defaultTemplate) {
        isDefaultTemplate = defaultTemplate;
    }
}
