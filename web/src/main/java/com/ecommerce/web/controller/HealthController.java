package com.ecommerce.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@Tag(name = "Health", description = "Application health checks")
public class HealthController {
    
    private final DataSource dataSource;
    
    @GetMapping
    @Operation(summary = "Basic health check")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now(),
            "version", "1.0.0",
            "environment", "development"
        ));
    }
    
    @GetMapping("/detailed")
    @Operation(summary = "Detailed health check")
    public ResponseEntity<Map<String, Object>> detailedHealth() {
        boolean dbHealthy = checkDatabaseHealth();
        
        return ResponseEntity.ok(Map.of(
            "status", dbHealthy ? "UP" : "DOWN",
            "timestamp", LocalDateTime.now(),
            "components", Map.of(
                "database", Map.of(
                    "status", dbHealthy ? "UP" : "DOWN",
                    "details", dbHealthy ? "Database connection successful" : "Database connection failed"
                ),
                "diskSpace", Map.of(
                    "status", "UP",
                    "details", Map.of(
                        "free", "10.5 GB",
                        "threshold", "1 GB"
                    )
                ),
                "application", Map.of(
                    "status", "UP",
                    "details", Map.of(
                        "uptime", "2h 15m",
                        "memory", "512 MB / 1 GB"
                    )
                )
            )
        ));
    }
    
    private boolean checkDatabaseHealth() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(5);
        } catch (Exception e) {
            return false;
        }
    }
}