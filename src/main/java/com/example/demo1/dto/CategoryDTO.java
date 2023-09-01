package com.example.demo1.dto;

import com.example.demo1.dao.entity.Categories;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CategoryDTO {
    private String category_id;
    private String parent_id;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public CategoryDTO(Categories categories) {
        this.category_id = categories.getCategory_id();
        this.parent_id = categories.getParent_id();
        this.name = categories.getName();
        this.created_at = categories.getCreated_at();
        this.updated_at = categories.getUpdated_at();
    }
    public CategoryDTO() {
    }
}

