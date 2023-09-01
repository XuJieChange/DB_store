package com.example.demo1.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class EmployeeRolesId implements Serializable {
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column(name = "role_id")
    private Integer roleId;
    public EmployeeRolesId() {}
    public EmployeeRolesId(Integer employeeId, Integer roleId) {
        this.employeeId = employeeId;
        this.roleId = roleId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRolesId that = (EmployeeRolesId) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, roleId);
    }
}
