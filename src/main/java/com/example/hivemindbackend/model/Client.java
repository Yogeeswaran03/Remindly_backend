package com.example.hivemindbackend.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFollowUpMessage() {
        return followUpMessage;
    }

    public void setFollowUpMessage(String followUpMessage) {
        this.followUpMessage = followUpMessage;
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

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String name;
    private String email;
    private String invoiceLink;
    private String followUpMessage;
    private LocalDate followUpStartDate;
    private int followUpFrequency; // in days
    private LocalDate followUpTillDate;
    private boolean status;

    // Getters and Setters
}