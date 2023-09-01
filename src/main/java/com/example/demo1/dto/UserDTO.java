package com.example.demo1.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Integer id;
    private String email;
    private String name;
    private String password;
    private boolean is_delete;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public boolean getIs_delete() {
        return is_delete;
    }

    public void isIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
