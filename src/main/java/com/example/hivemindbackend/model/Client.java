package com.example.hivemindbackend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "templateId", nullable = false)
    private Template template;

    private String name;
    private String email;
    private String invoiceLink;
    private LocalDate followUpStartDate;
    private int followUpFrequency; // in days
    private LocalDate followUpTillDate;
    private boolean status;

    // Constructors
    public Client() {}

    public Client(User user, Template template, String name, String email, String invoiceLink,
                  LocalDate followUpStartDate, int followUpFrequency, LocalDate followUpTillDate, boolean status) {
        this.user = user;
        this.template = template;
        this.name = name;
        this.email = email;
        this.invoiceLink = invoiceLink;
        this.followUpStartDate = followUpStartDate;
        this.followUpFrequency = followUpFrequency;
        this.followUpTillDate = followUpTillDate;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvoiceLink() {
        return invoiceLink;
    }

    public void setInvoiceLink(String invoiceLink) {
        this.invoiceLink = invoiceLink;
    }

    public LocalDate getFollowUpStartDate() {
        return followUpStartDate;
    }

    public void setFollowUpStartDate(LocalDate followUpStartDate) {
        this.followUpStartDate = followUpStartDate;
    }

    public int getFollowUpFrequency() {
        return followUpFrequency;
    }

    public void setFollowUpFrequency(int followUpFrequency) {
        this.followUpFrequency = followUpFrequency;
    }

    public LocalDate getFollowUpTillDate() {
        return followUpTillDate;
    }

    public void setFollowUpTillDate(LocalDate followUpTillDate) {
        this.followUpTillDate = followUpTillDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
