package com.example.demo1.dao;

import com.example.demo1.dao.entity.AvailableProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Ava_ProductRepository extends JpaRepository<AvailableProducts, Integer> {
//    List<AvailableProducts> findByName(String name);
}

