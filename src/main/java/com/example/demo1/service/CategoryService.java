package com.example.demo1.service;

import com.example.demo1.dao.CategoryRepository;
import com.example.demo1.dto.CategoryDTO;
import com.example.demo1.dao.entity.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Categories createCategory(CategoryDTO categoryDTO) {
        Categories categories = new Categories();
        categories.setCategory_id(categoryDTO.getCategory_id());
        categories.setParent_id(categoryDTO.getParent_id());
        categories.setName(categoryDTO.getName());
        categories.setCreated_at(LocalDateTime.now());
//        categoryDTO.setUpdated_at(LocalDateTime.now());
        // set other fields if available
        categoryRepository.save(categories);
        return categories;
    }
    public Categories updateCategory(CategoryDTO categoryDTO) {
        Categories existingCategories=categoryRepository.findById(categoryDTO.getCategory_id()).orElse(null);
        if ((existingCategories != null)) {
            existingCategories.setName(categoryDTO.getName());
            existingCategories.setParent_id(categoryDTO.getParent_id());
            return categoryRepository.save(existingCategories);

        }
        return null;
    }

    public List<CategoryDTO> getAllCategories(){
        List<Categories> categories=categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean deleteCategory(String category_id) {
        Optional<Categories> existingCategory=categoryRepository.findById(category_id);
        if (existingCategory.isPresent()) {
            categoryRepository.delete(existingCategory.get());
            return true;//成功刪除
        }
        return false;//分類不存在
    }
    private CategoryDTO convertToDTO(Categories categories) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategory_id(categories.getCategory_id());
        dto.setName(categories.getName());
        dto.setParent_id(categories.getParent_id());
        dto.setCreated_at(categories.getCreated_at());
        dto.setUpdated_at(categories.getUpdated_at());
        // 如果EmployeeDTO还有其他属性，例如created_at或updated_at，也应在这里设置
        return dto;
    }
}
