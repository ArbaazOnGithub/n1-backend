package com.n1solution.services;

import com.n1solution.entities.ContactMessage;
import com.n1solution.repositories.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Autowired
    private EmailService emailService;

    public ContactMessage saveMessage(ContactMessage message) {
        message.setCreatedAt(Instant.now());
        ContactMessage saved = contactMessageRepository.save(message);
        
        // Notify admin via email
        emailService.sendContactFormAdminNotification(saved);
        
        return saved;
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAllByOrderByCreatedAtDesc();
    }

    public void deleteMessage(Long id) {
        contactMessageRepository.deleteById(id);
    }
}
