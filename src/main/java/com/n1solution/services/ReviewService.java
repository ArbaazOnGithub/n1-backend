package com.n1solution.services;

import com.n1solution.entities.Review;
import com.n1solution.entities.User;
import com.n1solution.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    public Review createReview(int rating, String comment, String serviceName, Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setServiceName(serviceName);
        review.setCreatedAt(Instant.now());
        review.setUser(user);
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findByApprovedTrueOrderByCreatedAtDesc();
    }

    public List<Review> getAdminReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc();
    }

    public void approveReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        review.setApproved(true);
        reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsByService(String serviceName) {
        return reviewRepository.findByServiceNameOrderByCreatedAtDesc(serviceName);
    }
}
