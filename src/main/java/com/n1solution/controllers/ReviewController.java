package com.n1solution.controllers;

import com.n1solution.dto.ReviewDTO;
import com.n1solution.entities.Review;
import com.n1solution.services.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Review createReview(@RequestBody ReviewDTO dto, HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User must be logged in to submit a review.");
        }
        return reviewService.createReview(dto.getRating(), dto.getComment(), dto.getServiceName(), userId);
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/service/{serviceName}")
    public List<Review> getReviewsByService(@PathVariable String serviceName) {
        return reviewService.getReviewsByService(serviceName);
    }
}
