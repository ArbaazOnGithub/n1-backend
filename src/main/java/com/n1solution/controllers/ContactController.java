package com.n1solution.controllers;

import com.n1solution.entities.ContactMessage;
import com.n1solution.services.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping("/submit")
    public ContactMessage submitContactForm(@RequestBody ContactMessage message) {
        return contactMessageService.saveMessage(message);
    }

    @GetMapping("/admin")
    public List<ContactMessage> getAllMessages() {
        return contactMessageService.getAllMessages();
    }

    @DeleteMapping("/admin/{id}")
    public void deleteMessage(@PathVariable Long id) {
        contactMessageService.deleteMessage(id);
    }
}
