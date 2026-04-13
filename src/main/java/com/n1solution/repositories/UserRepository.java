package com.n1solution.repositories;

import com.n1solution.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

	List<User> findByRegistrationDate(LocalDate today);
}