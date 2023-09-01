package com.example.demo1.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "Employees")
@Data  // 使用Lombok自動生成getters和setters
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
    @Column(name = "is_delete")
    private Boolean isDelete;
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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

}
