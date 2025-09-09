package com.ecommerce.web.controller;

import com.ecommerce.core.entity.Order;
import com.ecommerce.core.entity.Product;
import com.ecommerce.core.entity.User;
import com.ecommerce.core.enums.OrderStatus;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "Admin operations")
public class AdminController {
    
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    
    @GetMapping("/dashboard")
    @Operation(summary = "Get admin dashboard data")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        
        // Simulate dashboard data
        return ResponseEntity.ok(Map.of(
            "totalUsers", 1250,
            "totalOrders", 3420,
            "totalRevenue", 125000.50,
            "pendingOrders", 45,
            "lowStockProducts", 12,
            "todaysSales", 5670.25,
            "conversionRate", 3.2,
            "avgOrderValue", 85.30
        ));
    }
    
    @GetMapping("/analytics/sales")
    @Operation(summary = "Get sales analytics")
    public ResponseEntity<Map<String, Object>> getSalesAnalytics(
            @RequestParam(defaultValue = "30") int days) {
        
        // Simulate sales analytics
        return ResponseEntity.ok(Map.of(
            "period", days + " days",
            "totalSales", 45670.80,
            "orderCount", 234,
            "avgOrderValue", 195.20,
            "topCategories", List.of(
                Map.of("name", "Electronics", "sales", 15670.50),
                Map.of("name", "Clothing", "sales", 12340.30),
                Map.of("name", "Books", "sales", 8950.20)
            ),
            "dailySales", List.of(
                Map.of("date", "2025-01-01", "sales", 1250.50),
                Map.of("date", "2025-01-02", "sales", 1890.75),
                Map.of("date", "2025-01-03", "sales", 2100.25)
            )
        ));
    }
    
    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public ResponseEntity<Map<String, Object>> getUsers(Pageable pageable) {
        
        // Simulate user list
        return ResponseEntity.ok(Map.of(
            "users", List.of(
                Map.of("id", 1, "username", "john_doe", "email", "john@example.com", "role", "CUSTOMER"),
                Map.of("id", 2, "username", "jane_smith", "email", "jane@example.com", "role", "CUSTOMER")
            ),
            "totalElements", 1250,
            "totalPages", 125,
            "currentPage", 0
        ));
    }
    
    @PostMapping("/users/{id}/lock")
    @Operation(summary = "Lock user account")
    public ResponseEntity<Map<String, String>> lockUser(@PathVariable Long id) {
        userService.lockAccount(id);
        return ResponseEntity.ok(Map.of("message", "User account locked successfully"));
    }
    
    @PostMapping("/users/{id}/unlock")
    @Operation(summary = "Unlock user account")
    public ResponseEntity<Map<String, String>> unlockUser(@PathVariable Long id) {
        userService.unlockAccount(id);
        return ResponseEntity.ok(Map.of("message", "User account unlocked successfully"));
    }
    
    @GetMapping("/orders/pending")
    @Operation(summary = "Get pending orders")
    public ResponseEntity<List<Map<String, Object>>> getPendingOrders() {
        
        // Simulate pending orders
        return ResponseEntity.ok(List.of(
            Map.of(
                "id", 1,
                "orderNumber", "ORD-001",
                "customerName", "John Doe",
                "totalAmount", 299.99,
                "status", "PENDING",
                "createdAt", LocalDateTime.now().minusHours(2)
            ),
            Map.of(
                "id", 2,
                "orderNumber", "ORD-002",
                "customerName", "Jane Smith",
                "totalAmount", 159.50,
                "status", "CONFIRMED",
                "createdAt", LocalDateTime.now().minusHours(1)
            )
        ));
    }
    
    @GetMapping("/products/low-stock")
    @Operation(summary = "Get low stock products")
    public ResponseEntity<List<Map<String, Object>>> getLowStockProducts() {
        
        // Simulate low stock products
        return ResponseEntity.ok(List.of(
            Map.of(
                "id", 1,
                "name", "iPhone 15 Pro",
                "sku", "IPHONE15PRO001",
                "currentStock", 5,
                "minStock", 10,
                "status", "LOW_STOCK"
            ),
            Map.of(
                "id", 2,
                "name", "MacBook Air",
                "sku", "MACBOOK001",
                "currentStock", 2,
                "minStock", 5,
                "status", "CRITICAL"
            )
        ));
    }
}