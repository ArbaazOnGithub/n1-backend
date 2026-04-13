package com.n1solution.dto;

import java.time.Instant;

public class OrderDetailsDTO {
    private Long orderId;
    private String serviceType;
    private Instant orderDate;
    private String status;
    private String userName;
    private String userNumber;

    // Constructor to match the query
    public OrderDetailsDTO(Long orderId, String serviceType, Instant orderDate, String status, String userName, String userNumber) {
        this.orderId = orderId;
        this.serviceType = serviceType;
        this.orderDate = orderDate;
        this.status = status;
        this.userName = userName;
        this.userNumber = userNumber;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}