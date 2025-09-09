package com.ecommerce.service;

import com.ecommerce.core.entity.Product;
import com.ecommerce.service.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    
    Product createProduct(ProductDto productDto);
    Optional<Product> findById(Long id);
    Page<Product> findAll(Pageable pageable);
    Page<Product> searchProducts(String keyword, Pageable pageable);
    Page<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    List<Product> findFeaturedProducts();
    List<Product> getRecommendations(Long userId, int limit);
    Product updateStock(Long productId, Integer quantity);
    void deleteProduct(Long id);
}