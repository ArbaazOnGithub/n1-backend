package com.n1solution.services;

import com.n1solution.entities.User;
import com.n1solution.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void promoteToAdmin() {
        String adminEmail = "mohd.arbaaz.job@gmail.com";
        Optional<User> userOpt = userRepository.findByEmail(adminEmail);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getRole() != User.Role.ROLE_ADMIN) {
                user.setRole(User.Role.ROLE_ADMIN);
                userRepository.save(user);
                System.out.println("USER PROMOTED TO ADMIN: " + adminEmail);
            }
        }
    }

    public User createUser(User user) {
        // Check if the email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists.");
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User toggleBlockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBlocked(!user.isBlocked());
        return userRepository.save(user);
    }

    public List<User> findByRegistrationDate() {
        LocalDate today = LocalDate.now();
        return userRepository.findByRegistrationDate(today);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Add this method to fetch a user by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}