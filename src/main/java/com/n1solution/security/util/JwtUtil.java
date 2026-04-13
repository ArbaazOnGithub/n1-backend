package com.n1solution.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final String SECRET_KEY = "your-secret-key"; // Replace with your actual secret key
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    public String generateToken(UserDetails userDetails, Long userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withSubject(userDetails.getUsername()) // Use email as the subject
                    .withClaim("role", userDetails.getAuthorities().iterator().next().getAuthority()) // Add role to the token
                    .withClaim("userId", userId) // Add userId to the token
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 10 hours
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            logger.error("Error generating JWT token: {}", exception.getMessage());
            throw new RuntimeException("Error generating JWT token", exception);
        }
    }

    public String extractUsername(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException e) {
            logger.error("Error extracting username from token: {}", e.getMessage());
            throw new RuntimeException("Invalid or expired token");
        }
    }

    public Long extractUserId(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

            // Extract the userId claim
            Claim userIdClaim = jwt.getClaim("userId");

            // Check if the claim exists and is not null
            if (userIdClaim.isNull()) {
                logger.error("userId claim is missing or null in the token");
                throw new RuntimeException("userId claim is missing or null in the token");
            }

            // Return the userId as a Long
            return userIdClaim.asLong();
        } catch (JWTVerificationException e) {
            logger.error("Error extracting userId from token: {}", e.getMessage());
            throw new RuntimeException("Invalid or expired token");
        } catch (Exception e) {
            logger.error("Unexpected error extracting userId from token: {}", e.getMessage());
            throw new RuntimeException("Failed to extract userId from token");
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username != null && username.equals(userDetails.getUsername()));
        } catch (Exception e) {
            logger.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }
}