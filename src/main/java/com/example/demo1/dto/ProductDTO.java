package com.example.demo1.dto;

import com.example.demo1.dao.entity.Categories;
import com.example.demo1.dao.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductDTO {
    private Integer product_id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock_quantity;
    private String image_url;
    private List<String> categoryIds;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    public ProductDTO(Product product) {
        this.product_id = product.getProduct_id();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock_quantity = product.getStock_quantity();
        this.image_url = product.getImage_url();
        this.categoryIds = product.getCategories().stream()
                .map(Categories::getCategory_id)
                .collect(Collectors.toList());
        this.created_at = product.getCreated_at();
        this.updated_at = product.getUpdated_at();
        // 如果Product還有其他與ProductDTO匹配的屬性，請在這裡設置它們
    }
    public ProductDTO() {

    }
    public Product toProduct(){//insert
        Product product= new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setStock_quantity(this.stock_quantity);
        product.setImage_url(this.image_url);
        return product;
    }
    public Product updateProduct(Product existingProduct){
        existingProduct.setName(this.name);
        existingProduct.setDescription(this.description);
        existingProduct.setPrice(this.price);
        existingProduct.setStock_quantity(this.stock_quantity);
        existingProduct.setImage_url(this.image_url);
        return existingProduct;
    }

}
