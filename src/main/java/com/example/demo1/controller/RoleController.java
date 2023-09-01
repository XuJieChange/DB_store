package com.example.demo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo1.ErrorResponse;
import com.example.demo1.dto.RoleDTO;
import com.example.demo1.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        System.out.println(roleDTO);
        roleService.createRole(roleDTO);
        return roleDTO;
    }

    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {

        // 確保DTO的ID和URL中的ID是相同的
        if (!id.equals(roleDTO.getId())) {
            // 創建一個錯誤消息和HTTP 400 Bad Request響應
            return new ResponseEntity<>(
                    new ErrorResponse("ID in URL does not match ID in request body."),
                    HttpStatus.BAD_REQUEST
            );
        }

        System.out.println(roleDTO);

        try {
            roleService.updateRole(roleDTO);
            // 如果一切正常，返回更新後的employeeDTO和HTTP 200 OK響應
            return new ResponseEntity<>(roleDTO, HttpStatus.OK);
        } catch(Exception e) {
            // Handle other unexpected errors
            return new ResponseEntity<>(
                    new ErrorResponse("Error updating the employee."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {

        try {
            boolean deleted = roleService.deleteRole(id);
            if (deleted) {
                System.out.println("Success Delete");
                return new ResponseEntity<>("已刪除用戶呦!",HttpStatus.OK); // 返回204 No Content當刪除成功

            } else {
                // 創建一個錯誤消息和HTTP 404 Not Found響應，當ID不存在於資料庫時
                return new ResponseEntity<>(
                        new ErrorResponse("Employee with ID " + id + " not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e) {
            // Handle other unexpected errors
            return new ResponseEntity<>(
                    new ErrorResponse("Error deleting the employee."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
