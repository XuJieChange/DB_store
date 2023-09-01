package com.example.demo1.dao;

import com.example.demo1.dao.entity.EmployeeRoles;
import com.example.demo1.dao.entity.EmployeeRolesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRolesRepository extends JpaRepository<EmployeeRoles, EmployeeRolesId> {

}
