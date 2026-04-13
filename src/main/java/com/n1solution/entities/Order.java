package com.n1solution.entities;

import java.time.Instant;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity 
@Table(name = "orders")
@Data
public class Order {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceType;

    @Column(name = "order_date") // Customize column name if needed
    private Instant date;

    private String status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> details;

    @ManyToOne(fetch = FetchType.LAZY) // Many orders can belong to one user
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to the User table
    private User user;
}