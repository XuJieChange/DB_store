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
@Table(name = "roles")
public class Roles {

    @Id
//    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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

    @ManyToMany(mappedBy = "roles",cascade = CascadeType.MERGE)
    private Set<Employees>employees=new HashSet<>();



}
