package com.ecommerce.web.controller;

import com.ecommerce.core.entity.Product;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
@Tag(name = "Home", description = "Home page content and personalization")
public class HomeController {
    
    private final ProductService productService;
    
    @GetMapping
    @Operation(summary = "Get home page content")
    public ResponseEntity<Map<String, Object>> getHomePageContent(
            @RequestParam(required = false) Long userId) {
        
        List<Product> featuredProducts = productService.findFeaturedProducts();
        List<Product> recommendations = userId != null ? 
            productService.getRecommendations(userId, 10) : List.of();
        
        Map<String, Object> content = Map.of(
            "banners", List.of(
                Map.of("id", 1, "title", "Summer Sale", "image", "/images/banner1.jpg"),
                Map.of("id", 2, "title", "New Arrivals", "image", "/images/banner2.jpg")
            ),
            "featuredProducts", featuredProducts,
            "recommendations", recommendations,
            "categories", List.of(
                Map.of("id", 1, "name", "Electronics", "image", "/images/electronics.jpg"),
                Map.of("id", 2, "name", "Fashion", "image", "/images/fashion.jpg")
            ),
            "deals", List.of(
                Map.of("title", "Deal of the Day", "discount", "50% OFF", "product", featuredProducts.isEmpty() ? null : featuredProducts.get(0))
            )
        );
        
        return ResponseEntity.ok(content);
    }
    
    @GetMapping("/trending")
    @Operation(summary = "Get trending products")
    public ResponseEntity<List<Product>> getTrendingProducts(
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Product> trending = productService.findFeaturedProducts();
        return ResponseEntity.ok(trending.stream().limit(limit).toList());
    }
    
    @GetMapping("/deals")
    @Operation(summary = "Get current deals")
    public ResponseEntity<Map<String, Object>> getCurrentDeals() {
        return ResponseEntity.ok(Map.of(
            "flashSale", Map.of(
                "title", "Flash Sale",
                "endsAt", System.currentTimeMillis() + 3600000, // 1 hour
                "products", productService.findFeaturedProducts().stream().limit(5).toList()
            ),
            "dailyDeals", Map.of(
                "title", "Daily Deals",
                "products", productService.findFeaturedProducts().stream().limit(8).toList()
            )
        ));
    }
}