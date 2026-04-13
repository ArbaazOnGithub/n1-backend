package com.n1solution.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
//@CrossOrigin(origins = "http://localhost:3000")
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
    public User registerUser(@RequestBody User user) {
        return userService.createUser(user);
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