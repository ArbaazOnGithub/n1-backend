package com.n1solution.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n1solution.entities.User;
import com.n1solution.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/{id}/toggle-block")
    public User toggleBlockUser(@PathVariable Long id) {
        return userService.toggleBlockUser(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User created = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            String message = e.getMessage();
            if (message != null && message.toLowerCase().contains("email already exists")) {
                // 409 Conflict — duplicate email, return clear JSON message
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "This email is already registered. Please sign in."));
            }
            // 400 Bad Request for other validation errors
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", message != null ? message : "Registration failed. Please try again."));
        }
    }

    @GetMapping("/new-users")
    public List<User> getNewUsers() {
        return userService.findByRegistrationDate();
    }

    @DeleteMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}