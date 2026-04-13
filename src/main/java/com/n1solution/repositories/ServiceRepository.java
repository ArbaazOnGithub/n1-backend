package com.n1solution.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.n1solution.entities.Service;

@Repository
public interface ServiceRepository extends JpaRepository <Service, Long> {
}
