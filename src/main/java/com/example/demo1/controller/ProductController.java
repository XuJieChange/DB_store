package com.example.demo1.controller;

import com.example.demo1.dao.entity.AvailableProducts;
import com.example.demo1.dao.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo1.ErrorResponse;
import com.example.demo1.dto.ProductDTO;
import com.example.demo1.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDao, @RequestParam List<String> categoryIds) {
        try{
            //使用DTO和class創建商品
            Product createdProduct=productService.createProductWithCategories(productDao.toProduct(), categoryIds);
            //將創建的商品轉換成DTO並返回
            ProductDTO createProductDTO=new ProductDTO(createdProduct);//假設ProductDTO有一個接受Product的構造器
            return new ResponseEntity<>(createProductDTO,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(
                    new ErrorResponse("Error creating the product"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {

        // 確保DTO的ID和URL中的ID是相同的
        if (!id.equals(productDTO.getProduct_id())) {
            // 創建一個錯誤消息和HTTP 400 Bad Request響應
            return new ResponseEntity<>(
                    new ErrorResponse("ID in URL does not match ID in request body."),
                    HttpStatus.BAD_REQUEST
            );
        }

        System.out.println(productDTO);

        try {
            Product updatedProduct= productService.updateProductWithCategories(id, productDTO);
            ProductDTO updatedProductDTO=new ProductDTO(updatedProduct);
            // 如果一切正常，返回更新後的employeeDTO和HTTP 200 OK響應
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (Exception e) {
            // Handle other unexpected errors
            return new ResponseEntity<>(
                    new ErrorResponse("Error updating the product."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {

        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                System.out.println("Success Delete");
                return new ResponseEntity<>("已刪除用戶呦!", HttpStatus.OK); // 返回204 No Content當刪除成功

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
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }
    @PostMapping("/addToAvailable")
    public ResponseEntity<String> addToAvailable(@RequestParam Integer productId){
        try{
            productService.addToAvailableProducts(productId);
            return ResponseEntity.ok("商品成功上架");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/available")//查詢已上架的全部商品
    public ResponseEntity<List<AvailableProducts>>getAllAvailableProducts(){
        List<AvailableProducts> availableProducts=productService.getAllAvailableProducts();
        return ResponseEntity.ok(availableProducts);
    }
}
