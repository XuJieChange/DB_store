package com.example.demo1.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="products")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer product_id;// This will be manually set
    private String name;
    private String description;
    private Integer price;
    @Column(name = "stock_quantity")
    private Integer stock_quantity;
    @Column(name = "image_url")
    private String image_url = "test";  // 設定為預設值 "test"
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Categories> categories = new HashSet<>();
}
