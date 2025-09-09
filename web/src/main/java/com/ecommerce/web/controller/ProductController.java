package com.ecommerce.web.controller;

import com.ecommerce.core.entity.Product;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.dto.ProductDto;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management and search")
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    @Operation(summary = "Get all products")
    @Cacheable("products")
    @Timed(value = "api.products.get", description = "Time taken to get products")
    @Counted(value = "api.products.requests", description = "Number of product requests")
    @RateLimiter(name = "api")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<Product> products = productService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return productService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search products")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        PageRequest pageRequest = PageRequest.of(page, size);
        
        if (minPrice != null && maxPrice != null) {
            Page<Product> products = productService.findByPriceRange(minPrice, maxPrice, pageRequest);
            return ResponseEntity.ok(products);
        }
        
        Page<Product> products = productService.searchProducts(keyword, pageRequest);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/featured")
    @Operation(summary = "Get featured products")
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        List<Product> products = productService.findFeaturedProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}/recommendations")
    @Operation(summary = "Get product recommendations")
    public ResponseEntity<List<Product>> getRecommendations(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "5") int limit) {
        
        List<Product> recommendations = productService.getRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @Operation(summary = "Create new product")
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productService.createProduct(productDto);
        return ResponseEntity.ok(product);
    }
    
    @PutMapping("/{id}/stock")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @Operation(summary = "Update product stock")
    public ResponseEntity<Product> updateStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        
        Product product = productService.updateStock(id, quantity);
        return ResponseEntity.ok(product);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @Operation(summary = "Delete product")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}