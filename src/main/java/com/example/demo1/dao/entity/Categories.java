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
@Table(name="categories")
public class Categories {
    @Id
    private String category_id;
    private String parent_id;  // 可能會需要特殊處理，如自關聯
    private String name;
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

    @ManyToMany(mappedBy = "categories",cascade = {CascadeType.ALL})
    private Set<Product> products = new HashSet<>();
}
