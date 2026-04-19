package com.n1solution.services;

import com.n1solution.dto.OrderDetailsDTO;
import com.n1solution.entities.Order;
import com.n1solution.exceptions.OrderNotFoundException;
import com.n1solution.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        String oldStatus = order.getStatus();
        order.setStatus(status);
        Order savedOrder = orderRepository.save(order);

        // Send notification if status actually changed or if it was just pending
        if (savedOrder.getUser() != null && savedOrder.getUser().getEmail() != null) {
            emailService.sendOrderStatusUpdate(
                savedOrder.getUser().getEmail(), 
                savedOrder.getServiceType(), 
                status
            );
        }
        
        return savedOrder;
    }

    // Add this method to handle order creation
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
    
    
    public List<OrderDetailsDTO> getAllOrderDetailsWithUserInfo() {
        return orderRepository.findAllOrderDetailsWithUserInfo();
    }
    
    
 // Add this method to delete an order by ID
    
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }
    
    public long countOrdersByUserId(Long userId) {
        return orderRepository.countByUserId(userId);
    }
    
    
    public Order getOrderDetails(Long orderId) {
        return orderRepository.findById(orderId)
                              .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}