package com.ecommerce.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminDashboardController {
    
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        return ResponseEntity.ok(Map.of(
            "totalUsers", 1250,
            "totalOrders", 3450,
            "totalRevenue", 125000.50,
            "pendingOrders", 45
        ));
    }
    
    @GetMapping("/analytics/sales")
    public ResponseEntity<Map<String, Object>> getSalesAnalytics() {
        return ResponseEntity.ok(Map.of(
            "dailySales", 15000.0,
            "monthlySales", 450000.0,
            "topProducts", List.of("iPhone", "MacBook", "AirPods")
        ));
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getUsers() {
        return ResponseEntity.ok(List.of(
            Map.of("id", 1, "username", "john_doe", "email", "john@example.com", "status", "ACTIVE"),
            Map.of("id", 2, "username", "jane_smith", "email", "jane@example.com", "status", "ACTIVE")
        ));
    }
    
    @PostMapping("/users/{id}/lock")
    public ResponseEntity<Map<String, String>> lockUser(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("message", "User locked successfully"));
    }
    
    @GetMapping("/orders/pending")
    public ResponseEntity<List<Map<String, Object>>> getPendingOrders() {
        return ResponseEntity.ok(List.of(
            Map.of("id", 1, "orderNumber", "ORD-001", "status", "PENDING", "amount", 299.99),
            Map.of("id", 2, "orderNumber", "ORD-002", "status", "PENDING", "amount", 599.99)
        ));
    }
    
    @GetMapping("/products/low-stock")
    public ResponseEntity<List<Map<String, Object>>> getLowStockProducts() {
        return ResponseEntity.ok(List.of(
            Map.of("id", 1, "name", "iPhone 15", "stock", 5, "threshold", 10),
            Map.of("id", 2, "name", "MacBook Pro", "stock", 3, "threshold", 5)
        ));
    }
}