package com.n1solution.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity 
@Data 
public class OrderDetail {
    @Id 
    @GeneratedValue(strategy = GenerationType .IDENTITY)
    private Long id;
    private String name;
    private String value;
}
