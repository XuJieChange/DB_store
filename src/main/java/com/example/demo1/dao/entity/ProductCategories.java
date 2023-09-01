package com.example.demo1.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="product_categories")
public class ProductCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String product_category_id;
    private Integer product_id;
    private String category_id;

}
