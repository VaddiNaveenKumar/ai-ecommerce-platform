package com.ecommerce.web.controller;

import com.ecommerce.core.entity.Product;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Tag(name = "AI Services", description = "AI-powered features")
public class AIController {
    
    private final ProductService productService;
    private final Random random = new Random();
    
    @GetMapping("/recommendations/{userId}")
    @Operation(summary = "Get personalized recommendations")
    public ResponseEntity<List<Product>> getRecommendations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "5") int limit) {
        
        // Simple AI simulation - get random featured products
        List<Product> recommendations = productService.findFeaturedProducts()
                .stream()
                .limit(limit)
                .toList();
        
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/pricing/{productId}")
    @Operation(summary = "Get dynamic pricing")
    public ResponseEntity<Map<String, Object>> getDynamicPricing(@PathVariable Long productId) {
        Product product = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        // Simple dynamic pricing simulation
        BigDecimal basePrice = product.getBasePrice();
        double demandFactor = 0.8 + (random.nextDouble() * 0.4); // 0.8 to 1.2
        BigDecimal dynamicPrice = basePrice.multiply(BigDecimal.valueOf(demandFactor));
        
        return ResponseEntity.ok(Map.of(
            "productId", productId,
            "basePrice", basePrice,
            "dynamicPrice", dynamicPrice,
            "demandFactor", demandFactor,
            "recommendation", dynamicPrice.compareTo(basePrice) < 0 ? "BUY_NOW" : "WAIT"
        ));
    }
    
    @PostMapping("/fraud/analyze")
    @Operation(summary = "Analyze fraud risk")
    public ResponseEntity<Map<String, Object>> analyzeFraud(@RequestBody Map<String, Object> transaction) {
        
        // Simple fraud detection simulation
        double amount = Double.parseDouble(transaction.get("amount").toString());
        String location = transaction.get("location").toString();
        
        double riskScore = 0.1; // Base risk
        
        // High amount increases risk
        if (amount > 1000) riskScore += 0.3;
        if (amount > 5000) riskScore += 0.4;
        
        // International transactions have higher risk
        if (!location.equals("domestic")) riskScore += 0.2;
        
        String riskLevel = riskScore < 0.3 ? "LOW" : riskScore < 0.7 ? "MEDIUM" : "HIGH";
        
        return ResponseEntity.ok(Map.of(
            "riskScore", riskScore,
            "riskLevel", riskLevel,
            "recommendation", riskScore > 0.7 ? "BLOCK" : "APPROVE",
            "factors", List.of(
                amount > 1000 ? "High amount" : "Normal amount",
                !location.equals("domestic") ? "International" : "Domestic"
            )
        ));
    }
    
    @GetMapping("/analytics/customer/{userId}")
    @Operation(summary = "Get customer analytics")
    public ResponseEntity<Map<String, Object>> getCustomerAnalytics(@PathVariable Long userId) {
        
        // Simple customer analytics simulation
        return ResponseEntity.ok(Map.of(
            "customerId", userId,
            "lifetimeValue", 1250.50,
            "churnProbability", 0.15,
            "preferredCategories", List.of("Electronics", "Books", "Clothing"),
            "avgOrderValue", 85.30,
            "loyaltyScore", 0.78,
            "nextPurchasePrediction", "7-14 days"
        ));
    }
}