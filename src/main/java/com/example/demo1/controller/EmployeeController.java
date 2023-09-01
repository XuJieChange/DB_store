package com.example.demo1.controller;

import com.example.demo1.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo1.ErrorResponse;
import com.example.demo1.dao.entity.Employees;
import com.example.demo1.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO, @RequestParam List<Integer> roleIds) {
        try{
            //use DTO and class create employee
            Employees createEmployee=employeeService.createEmployeeWithRoles(employeeDTO.toEmployee(), roleIds);
            //change employee to dto and back
            EmployeeDTO createEmployeeDTO=new EmployeeDTO(createEmployee);//假設EmployeeDTO有一個接受Employee的構造器
            return new ResponseEntity<>(createEmployeeDTO,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(
                    new ErrorResponse("Error creating the employee"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDTO employeeDTO) {
        if (! id.equals(employeeDTO.getId())){
            //create a wrong message and HTTP 400 Bad Request
            return new ResponseEntity<>(
                    new ErrorResponse("ID in URL does not match ID in request body"),
                    HttpStatus.BAD_REQUEST
            );
        }
        System.out.println(employeeDTO);

        try {
            Employees updatedEmployee=employeeService.updateEmployeeWithRoles(id,employeeDTO);
            EmployeeDTO updateEmployeeDTO=new EmployeeDTO(updatedEmployee);
            //if ture,back to update and give Http 200
            return new ResponseEntity<>(updateEmployeeDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(
                    new ErrorResponse("Error updating the employee"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    @GetMapping//localhost:8090/employees or employees?name=name
    public ResponseEntity<List<EmployeeDTO>> searchEmployees(@RequestParam(required = false) Optional<String> name) {
        List<EmployeeDTO> result = employeeService.searchEmployees(name);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")//localhost:8090/employees/{employeeid}
    private ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable Integer id) {
        Employees deletedEmployee = employeeService.markEmployeeAsDeleted(id);
        EmployeeDTO employeeDTO = new EmployeeDTO(deletedEmployee);
        return ResponseEntity.ok(employeeDTO);
    }
}


