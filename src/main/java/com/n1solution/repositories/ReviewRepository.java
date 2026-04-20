package com.n1solution.repositories;

import com.n1solution.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByOrderByCreatedAtDesc();
    List<Review> findByApprovedTrueOrderByCreatedAtDesc();
    List<Review> findByServiceNameOrderByCreatedAtDesc(String serviceName);
}
