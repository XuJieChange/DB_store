package com.example.demo1.dto;

import com.example.demo1.dao.entity.Employees;
import com.example.demo1.dao.entity.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class EmployeeDTO {
    private Integer id;
    private String name;
    private String password;
    @JsonProperty("is_delete")
    private boolean is_delete;
    private List<Integer> roleIds;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public EmployeeDTO(Employees employees) {
        this.id = employees.getId();
        this.name = employees.getName();
        this.password = employees.getPassword();
        this.is_delete = employees.getIsDelete();
        this.roleIds = employees.getRoles().stream()
                .map(Roles::getId)
                .collect(Collectors.toList());
        this.created_at = employees.getCreated_at();
        this.updated_at = employees.getUpdated_at();
        // 如果Product還有其他與ProductDTO匹配的屬性，請在這裡設置它們
    }
    public EmployeeDTO() {}
    public Employees toEmployee(){//insert
        Employees employees= new Employees();
        employees.setName(this.name);
        employees.setPassword(this.password);
        employees.setIsDelete(this.is_delete);
        return employees;
    }
    public Employees updateEmployee(Employees existingEmployee){
        existingEmployee.setName(this.name);
        existingEmployee.setPassword(this.password);
        existingEmployee.setIsDelete(this.is_delete);
        return existingEmployee;
    }



}