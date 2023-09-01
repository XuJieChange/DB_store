package com.example.demo1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo1.dao.entity.Employees;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {
    Optional<Employees> findByName(String name);

    List<Employees> findByNameContainingAndIsDeleteFalse(String name);
    List<Employees> findByIsDeleteTrue();//查詢被刪除的員工
    List<Employees> findByIsDeleteFalse();//查詢被刪除的員工
}
