package com.n1solution.controllers;

import com.n1solution.dto.OrderDetailsDTO;
import com.n1solution.entities.Order;
import com.n1solution.entities.User;
import com.n1solution.services.OrderService;
import com.n1solution.services.UserService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
//@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/{id}/update-status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @PostMapping
    public Order createOrder(@Valid @RequestBody CreateOrderRequest request, HttpServletRequest httpRequest) {
        // Retrieve the userId from the request attributes (set by the JWT filter)
    	
    	
        Long userId = (Long) httpRequest.getAttribute("userId");
        System.out.println("got the userId in the order COntroller"+userId);
        
        Order order = new Order();
        order.setServiceType(request.getServiceType());
        order.setDetails(request.getDetails());
        order.setDate(Instant.now());
        order.setStatus("Pending");

        // Fetch the user from the database using the userId
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        order.setUser(user); // Associate the user with the order

        return orderService.createOrder(order);
    }

    @GetMapping("/details")
    public List<OrderDetailsDTO> getAllOrderDetailsWithUserInfo() {
        return orderService.getAllOrderDetailsWithUserInfo();
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
    
    
    @GetMapping("/count")
    public long countOrdersByUserId(@RequestParam Long userId) {
        return orderService.countOrdersByUserId(userId);
    }
    
    
    @GetMapping("/{orderId}/details")
    public Order getOrderDetails(@PathVariable Long orderId) {
        return orderService.getOrderDetails(orderId);
    }
    
    @GetMapping("/my")
    public List<Order> getMyOrders(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User ID not found in request. User may not be logged in.");
        }
        return orderService.getOrdersByUserId(userId);
    }
}