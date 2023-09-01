package com.example.demo1.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RoleDTO {
    private Integer id;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
