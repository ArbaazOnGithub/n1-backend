package com.n1solution.entities;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int timesBought;
    private String imageUrl; // New field for image URL

    @OneToMany(cascade = jakarta.persistence.CascadeType.ALL, fetch = jakarta.persistence.FetchType.LAZY)
    private List<ServiceField> fields;

    private String slug;

    @jakarta.persistence.PrePersist
    @jakarta.persistence.PreUpdate
    public void generateSlug() {
        if (this.name != null) {
            this.slug = this.name.toLowerCase().replaceAll("[^a-z0-9\\-]", "-").replaceAll("-+", "-");
        }
    }
}