package com.example.demo1.service;

import com.example.demo1.dao.CategoryRepository;
import com.example.demo1.dao.ProductRepository;
import com.example.demo1.dao.entity.AvailableProducts;
import com.example.demo1.dao.entity.Categories;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo1.dao.Ava_ProductRepository;
import com.example.demo1.dto.ProductDTO;
import com.example.demo1.dao.entity.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private Ava_ProductRepository avaProductRepository;

    public Product createProduct(ProductDTO productDTO) {
        Product product=new Product();
        product.setProduct_id(productDTO.getProduct_id());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock_quantity(productDTO.getStock_quantity());
        product.setImage_url(productDTO.getImage_url());
        productDTO.setCreated_at(LocalDateTime.now());

        productRepository.save(product);
        return product;
    }

    public Product updateProduct(ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productDTO.getProduct_id()).orElse(null);
        if (existingProduct != null) {
            existingProduct.setProduct_id(productDTO.getProduct_id());
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setStock_quantity(productDTO.getStock_quantity());
            existingProduct.setImage_url(productDTO.getImage_url());
            productDTO.setCreated_at(existingProduct.getCreated_at());
            productDTO.setUpdated_at(LocalDateTime.now());
            return productRepository.save(existingProduct);

        }
        return null;
    }

    public boolean deleteProduct(String id) {
        Optional<Product> existingProduct = productRepository.findById(Integer.valueOf(id));
        if (existingProduct.isPresent()) {
            productRepository.delete(existingProduct.get());
            return true;  // 成功刪除
        }
        return false;  // 員工不存在
    }
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProduct_id(product.getProduct_id());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock_quantity(product.getStock_quantity());
        dto.setImage_url(product.getImage_url());
        dto.setCreated_at(product.getCreated_at());
        dto.setUpdated_at(product.getUpdated_at());
        // 如果EmployeeDTO还有其他属性，例如created_at或updated_at，也应在这里设置
        return dto;
    }

    public void addToAvailableProducts(Integer productId){
        Optional<Product> productOpt=productRepository.findById(productId);
        if(productOpt.isPresent()){
            Product product=productOpt.get();

            AvailableProducts availableProducts=new AvailableProducts();
            availableProducts.setAvailable_product_id();
            availableProducts.setName(product.getName());
            availableProducts.setPrice(product.getPrice());
            availableProducts.setCreated_at(LocalDateTime.now());

            avaProductRepository.save(availableProducts);
        }else {
            throw new RuntimeException("商品未找到"+productId);
        }
    }
    public List<AvailableProducts> getAllAvailableProducts() {
        return avaProductRepository.findAll();
    }
    @Transactional
    public Product createProductWithCategories(Product product, List<String> categoryIds) {
        if(product == null || categoryIds == null) {
            throw new IllegalArgumentException("Product or categoryIds cannot be null");
        }

        Set<Categories> categories = categoryRepository.findAllById(categoryIds)
                .stream()
                .collect(Collectors.toSet());

        if(categories.size() != categoryIds.size()) {
            List<String> notFoundIds = categoryIds.stream()
                    .filter(id -> categories.stream().noneMatch(cat -> cat.getCategory_id().equals(id)))
                    .collect(Collectors.toList());
            throw new RuntimeException("Categories with IDs " + notFoundIds + " were not found!");
        }

        product.setCategories(categories);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProductWithCategories(Integer productId, ProductDTO productDTO){
        Product existingProduct=productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 使用 ProductDTO 中的方法更新 Product
        productDTO.updateProduct(existingProduct);

        //更新與categoryIds相關部分
        if (productDTO.getCategoryIds() != null && !productDTO.getCategoryIds().isEmpty()) {
            Set<Categories> categories= categoryRepository.findAllById(productDTO.getCategoryIds())
                    .stream()
                    .collect(Collectors.toSet());
            if(categories.size()!=productDTO.getCategoryIds().size()){
                throw new RuntimeException("Some categories were not found");
            }
            existingProduct.setCategories(categories);
        }
        return productRepository.save(existingProduct);
    }


}
