package com.example.demo1.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="employee_roles")
public class EmployeeRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private EmployeeRolesId id;

    @ManyToOne
    @JoinColumn(name="employee_id",insertable = false,updatable = false)
    private Employees employees;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Roles role;

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
}
