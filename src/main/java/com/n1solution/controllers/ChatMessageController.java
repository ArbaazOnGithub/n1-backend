package com.n1solution.controllers;

import com.n1solution.entities.ChatMessage;
import com.n1solution.entities.User;
import com.n1solution.repositories.ChatMessageRepository;
import com.n1solution.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String ADMIN_EMAIL = "mohd.arbaaz.job@gmail.com";

    private User getAuthenticatedUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("Unauthorized: No user session found");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Unauthorized: User not found"));
    }

    @PostMapping
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage messageRequest, HttpServletRequest request) {
        User currentUser = getAuthenticatedUser(request);
        ChatMessage msg = new ChatMessage();
        msg.setTimestamp(Instant.now());
        msg.setMessage(messageRequest.getMessage());

        if (currentUser.getRole() == User.Role.ROLE_ADMIN) {
            msg.setSenderEmail(ADMIN_EMAIL);
            msg.setReceiverEmail(messageRequest.getReceiverEmail());
            msg.setSystem(false);
        } else {
            msg.setSenderEmail(currentUser.getEmail());
            msg.setReceiverEmail(ADMIN_EMAIL);
            msg.setSystem(false);
        }

        ChatMessage saved = chatMessageRepository.save(msg);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getHistory(
            @RequestParam(value = "email", required = false) String email,
            HttpServletRequest request) {
        User currentUser = getAuthenticatedUser(request);

        String chatPartnerEmail;
        if (currentUser.getRole() == User.Role.ROLE_ADMIN) {
            if (email == null || email.trim().isEmpty()) {
                throw new RuntimeException("User email parameter is required for admin");
            }
            chatPartnerEmail = email;
        } else {
            chatPartnerEmail = ADMIN_EMAIL;
        }

        List<ChatMessage> history = chatMessageRepository.findChatHistory(currentUser.getEmail(), chatPartnerEmail);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/active")
    public ResponseEntity<List<String>> getActivePartners(HttpServletRequest request) {
        User currentUser = getAuthenticatedUser(request);
        if (currentUser.getRole() != User.Role.ROLE_ADMIN) {
            throw new RuntimeException("Forbidden: Admin access only");
        }
        List<String> partners = chatMessageRepository.findActiveChatPartners(ADMIN_EMAIL);
        return ResponseEntity.ok(partners);
    }
}
