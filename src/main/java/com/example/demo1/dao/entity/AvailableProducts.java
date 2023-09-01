package com.example.demo1.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="available_products")
public class AvailableProducts {
    @Id
    @Column(name = "available_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer available_product_id;
    private String name;
    private Integer price;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updated_at = LocalDateTime.now();
    }

    public void setAvailable_product_id() {
    }
}