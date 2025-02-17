package com.example.hivemindbackend.service;

import com.example.hivemindbackend.model.Template;
import com.example.hivemindbackend.model.User;
import com.example.hivemindbackend.repository.TemplateRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public void createDefaultTemplates(User user) {
        List<Template> defaultTemplates = List.of(
                new Template(user, "Welcome Email", "Special Offer for Settling Your Invoice", "Dear [Client's Name],\n" +
                        "I hope this message finds you well. We truly value the opportunity to work with you and are committed to providing the best service possible.\n" +
                        "We noticed that the payment for [invoice # or service name] dated [invoice date] is still pending. To make it easier for you to settle the payment and as a token of our appreciation, we’re delighted to offer you the following:\n" +
                        "[Describe Offer Here—Examples:\n" +
                        "●\tA [5% discount] on the outstanding amount if paid by [specific date].\n" +
                        "●\tComplimentary [service/product, e.g., one free consultation, priority support, or an upgrade].\n" +
                        "●\tEntry into a lucky draw/gift voucher worth [amount].**]\n" +
                        "This offer is valid until [specific date], so we encourage you to take advantage of it and settle the outstanding balance as soon as possible.\n" +
                        "We’ve attached the invoice again for your reference. Should you need assistance or have any questions, please don’t hesitate to reach out.\n" +
                        "Looking forward to your response and continuing our successful partnership.\n" +
                        "Warm regards,\n" +
                        " [Your Name]\n" +
                        " [Your Position]\n" +
                        " [Your Company Name]\n", true),
                new Template(user, "Gentle Reminder Email", "Friendly Reminder: Payment for [Invoice #]", "Dear [Client's Name],\n" +
                        "I hope this email finds you well. I wanted to kindly remind you that the payment for [invoice # or service name] was due on [due date]. As of now, we have not received the payment.\n" +
                        "We understand that delays can happen and would appreciate it if you could let us know if there’s anything we can do to assist in expediting the process. If the payment has already been made, kindly disregard this message.\n" +
                        "Please let me know if you need a copy of the invoice or any additional details. Looking forward to your response.\n" +
                        "Warm regards,\n" +
                        " [Your Name]\n" +
                        " [Your Position]\n" +
                        " [Your Company Name]\n", true),
                new Template(user, "Firm but Polite Follow-Up Email", "Follow-Up: Outstanding Payment for [Invoice #]", "Dear [Client's Name],\n" +
                        "I hope this message finds you well. I wanted to follow up on the payment for [invoice # or service name], which was due on [due date]. As of today, the payment remains outstanding.\n" +
                        "We greatly value our partnership and aim to ensure all projects run smoothly. Timely payments help us maintain this level of service and efficiency. Kindly let us know if there’s an issue we should be aware of or any way we can assist to resolve the delay.\n" +
                        "Please confirm when we can expect the payment, as this will help us plan accordingly. I have attached the invoice again for your reference.\n" +
                        "Looking forward to your reply.\n" +
                        "Best regards,\n" +
                        "[Your Name]\n" +
                        "[Your Position]\n" +
                        "[Your Company Name]\n", true),
                new Template(user, "Urgent Escalation Email (Final Notice)", "Urgent: Final Reminder for Payment [Invoice #]", "Dear [Client's Name],\n" +
                        "I hope you’re doing well. I am writing regarding the overdue payment for [invoice #], which was due on [due date]. Despite previous reminders, the payment is still outstanding.\n" +
                        "We truly value our collaboration and want to avoid any disruptions to our partnership. However, if the payment is not received by [specific deadline], we may need to consider alternative actions to resolve this matter.\n" +
                        "If you’ve already processed the payment, kindly share the confirmation details. Should you need assistance with the payment process or have concerns to discuss, please don’t hesitate to reach out.\n" +
                        "Thank you for your attention to this matter. I hope to resolve this promptly and continue working together.\n" +
                        "Kind regards,\n" +
                        " [Your Name]\n" +
                        " [Your Position]\n" +
                        " [Your Company Name]\n", true)
        );

        templateRepository.saveAll(defaultTemplates);
    }
    public List<Template> findByUser(User user) {
        return templateRepository.findByUser(user);
    }

    public List<Template> findDefaultTemplatesByUser(User user) {
        return templateRepository.findByUserAndIsDefaultTemplate(user, true);
    }

    public List<Template> findCustomTemplatesByUser(User user) {
        return templateRepository.findByUserAndIsDefaultTemplate(user, false);
    }

    public Template saveTemplate(Template template) {
        return templateRepository.save(template);
    }

    public Template getTemplateById(Long templateId) {
        return templateRepository.findById(templateId).orElse(null);
    }
    // Delete a template by ID
    public boolean deleteTemplate(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        if (template.isPresent()) {
            templateRepository.deleteById(templateId);
            return true;
        }
        return false;
    }
}
