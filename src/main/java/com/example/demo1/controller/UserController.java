package com.example.demo1.controller;

import com.example.demo1.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo1.ErrorResponse;
import com.example.demo1.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        System.out.println(userDTO);
        userService.createUser(userDTO);
        return userDTO;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {

        // 確保DTO的ID和URL中的ID是相同的
        if (!id.equals(userDTO.getId())) {
            // 創建一個錯誤消息和HTTP 400 Bad Request響應
            return new ResponseEntity<>(
                    new ErrorResponse("ID in URL does not match ID in request body."),
                    HttpStatus.BAD_REQUEST
            );
        }

        System.out.println(userDTO);

        try {
            userService.updateUser(userDTO);
            // 如果一切正常，返回更新後的employeeDTO和HTTP 200 OK響應
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch(Exception e) {
            // Handle other unexpected errors
            return new ResponseEntity<>(
                    new ErrorResponse("Error updating the employee."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {

        try {
            boolean deleted = userService.deleteUser(id);
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
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        System.out.println("Fetching employee with ID: " + id);
        return userService.findUserById(id);
    }

    @GetMapping("/search")
    public List<UserDTO> searchUsersByEmail(@RequestParam String email) {
        System.out.println("Fetching employee with Email: " + email);
        return userService.searchUsersByEmail(email);
    }
}
