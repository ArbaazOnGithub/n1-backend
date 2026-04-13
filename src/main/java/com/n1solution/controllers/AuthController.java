package com.n1solution.controllers;

import com.n1solution.dto.AuthenticationResponse;
import com.n1solution.entities.User;
import com.n1solution.security.util.JwtUtil;
import com.n1solution.security.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        // Load user details
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        // Generate JWT token with userId
        final String token = jwtUtil.generateToken(userDetails, ((User) userDetails).getId()); // Cast to User to get ID

        // Return the token in the response
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}