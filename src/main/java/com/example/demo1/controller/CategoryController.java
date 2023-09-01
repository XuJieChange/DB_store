package com.example.demo1.controller;

import com.example.demo1.dto.CategoryDTO;
import com.example.demo1.dao.entity.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo1.ErrorResponse;
import com.example.demo1.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping("/create")
    public CategoryDTO create(@RequestBody CategoryDTO categoryDTO) {
        Categories savedCategory = categoryService.createCategory(categoryDTO);
        return new CategoryDTO(savedCategory);
    }
    @PutMapping("/{id}")

    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        if (!id.equals(categoryDTO.getCategory_id())) {
            return new ResponseEntity<>(
                    new ErrorResponse("ID in URL does not match ID in request body."),
                    HttpStatus.BAD_REQUEST
            );
        }
        System.out.println(categoryDTO);
        try {
            categoryService.updateCategory(categoryDTO);
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Errpr updating the category."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    @DeleteMapping("/{category_id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String category_id) {
        try {
            boolean deleted = categoryService.deleteCategory(category_id);
            if (deleted) {
                System.out.println("Success delete");
                return new ResponseEntity<>("已刪除分類唷!!",HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new ErrorResponse("Category with Id" + category_id + "not found."),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Error deleting the category."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    @GetMapping
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }
}



