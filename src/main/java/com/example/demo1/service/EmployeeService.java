package com.example.demo1.service;

import com.example.demo1.dao.EmployeeRepository;
import com.example.demo1.dao.RoleRepository;
import com.example.demo1.dto.EmployeeDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo1.dao.entity.Employees;
import com.example.demo1.dao.entity.Roles;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Employees createEmployee(EmployeeDTO employeeDTO) {
        Employees employees=new Employees();
        employees.setId(employeeDTO.getId());
        employees.setName(employeeDTO.getName());
        employees.setPassword(employeeDTO.getPassword());
        employees.setIsDelete(employeeDTO.is_delete());
        employeeDTO.setCreated_at(LocalDateTime.now());
        employeeRepository.save(employees);
        return employees;
    }
    public Employees updateEmployee(EmployeeDTO employeeDTO) {
        Employees existingEmployee = employeeRepository.findById(employeeDTO.getId()).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setName(employeeDTO.getName());
            existingEmployee.setPassword(employeeDTO.getPassword());
            existingEmployee.setIsDelete(employeeDTO.is_delete());
            employeeDTO.setCreated_at(existingEmployee.getCreated_at());
            employeeDTO.setUpdated_at(LocalDateTime.now());
            employeeRepository.save(existingEmployee);
            return existingEmployee;
        }
        return null;
    }

    public List<EmployeeDTO> searchEmployees(Optional<String> name) {
        List<Employees> employeesList;

        if (name.isPresent() && !name.get().trim().isEmpty()) {
            employeesList=employeeRepository.findByNameContainingAndIsDeleteFalse(name.get());
        }else {
            employeesList=employeeRepository.findByIsDeleteFalse();
        }
        return employeesList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    @Transactional
    public Employees markEmployeeAsDeleted(Integer id) {
        Employees employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        employee.setIsDelete(true);
        return employeeRepository.save(employee);
    }
    private EmployeeDTO convertToDTO(Employees employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setPassword(employee.getPassword());
        dto.set_delete(employee.getIsDelete());
        dto.setCreated_at(employee.getCreated_at());
        dto.setUpdated_at(employee.getUpdated_at());
        // 如果EmployeeDTO还有其他属性，例如created_at或updated_at，也应在这里设置
        return dto;
    }
    @Transactional
    public Employees createEmployeeWithRoles(Employees employees,List<Integer> roleIds) {
        if (employees == null || roleIds == null) {
            throw new IllegalArgumentException("Employee or roleIds cannot be null");
        }
        Set<Roles> role=roleRepository.findAllById(roleIds)
                .stream()
                .collect(Collectors.toSet());

        if(role.size()!= roleIds.size()){
            List<Integer> notFoundIds=roleIds.stream()
                    .filter(id ->role.stream().noneMatch(cat->cat.getId().equals(id)))
                    .collect(Collectors.toList());
            throw new RuntimeException("Roles with IDs " + notFoundIds + " were not found!");
        }
        employees.setRoles(role);
        return employeeRepository.save(employees);
    }
    @Transactional
    public Employees updateEmployeeWithRoles(Integer employeeId, EmployeeDTO employeeDTO) {
        Employees existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        //使用 EmployeeDTO 中的方法更新 Employee
        employeeDTO.updateEmployee(existingEmployee);

        //update roleIds part
        if(employeeDTO.getRoleIds()!=null&& !employeeDTO.getRoleIds().isEmpty()){
            Set<Roles> roles=roleRepository.findAllById(employeeDTO.getRoleIds())
                    .stream()
                    .collect(Collectors.toSet());
            if ((roles.size()!=employeeDTO.getRoleIds().size())){
                throw new RuntimeException("Some roleIds were not found");
            }
            existingEmployee.setRoles(roles);
        }
        return employeeRepository.save(existingEmployee);
    }

//    public Employees addEmployee(Integer roleId,EmployeeDTO employeeDTO) {
//        if (roleId == null) {
//            throw new IllegalArgumentException("Role ID cannot be null");
//        }
//
//        // Fetch the role from the database using the given roleId
//        Optional<Roles> roleOpt = roleRepository.findById(roleId);
//
//        if (!roleOpt.isPresent()) {
//            throw new EntityNotFoundException("Role with ID " + roleId + " not found");
//        }
//
//        Roles role = roleOpt.get();
//
//        // Assuming you have a method to convert EmployeeDTO to Employee entity
//        Employees employees = convertDTOToEntity(employeeDTO);
//
//        // Set the role to the employee
//        Set<Roles> r = new HashSet<>();
//        r.add(role);
//        employees.setRoles(r);
//
//        // Save the employee to the database
//        employeeRepository.save(employees);
//        return employees;
//    }
//
//    private Employees convertDTOToEntity(EmployeeDTO employeeDTO) {
//        Employees employees = new Employees();
//
//        // Set fields from DTO to Entity, e.g.,
//        employees.setName(employeeDTO.getName());
//        //... set other fields as needed
//
//        return employees;
//    }
}







