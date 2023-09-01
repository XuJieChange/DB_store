package com.example.demo1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo1.dao.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);

//    Optional<Product> findById(Integer id);
}
