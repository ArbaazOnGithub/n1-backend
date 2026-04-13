package com.n1solution.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String mobile;
    private String address;
    private boolean blocked;
    private int orders;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER; // Default role

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }

    @Column(name = "registration_date")
    private LocalDate registrationDate = LocalDate.now();

    // Ensure the role is never null
    public Role getRole() {
        return role != null ? role : Role.ROLE_USER; // Default to ROLE_USER if null
    }

    public void setRole(Role role) {
        this.role = role != null ? role : Role.ROLE_USER; // Default to ROLE_USER if null
    }

    // Implement UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(getRole().name())); // Use getRole() to ensure non-null
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}