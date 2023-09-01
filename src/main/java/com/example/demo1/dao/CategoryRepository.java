package com.example.demo1.dao;

import com.example.demo1.dao.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, String> {
    Optional<Categories> findByName(String name);
}
