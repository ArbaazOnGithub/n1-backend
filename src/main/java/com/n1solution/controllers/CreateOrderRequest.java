package com.n1solution.controllers;

import com.n1solution.entities.OrderDetail;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateOrderRequest {
    private String serviceType;
    
//    @NotNull(message = "User ID must not be null")
    private Long userId; // Add this field
    private List<OrderDetail> details;

    // Getters and Setters
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }
}