package com.ecommerce.web.controller;

import com.ecommerce.web.service.ABTestingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Analytics and A/B testing")
public class AnalyticsController {
    
    private final ABTestingService abTestingService;
    
    @PostMapping("/events")
    @Operation(summary = "Track analytics event")
    public ResponseEntity<Map<String, String>> trackEvent(@RequestBody Map<String, Object> event) {
        String eventType = (String) event.get("type");
        Long userId = Long.valueOf(event.get("userId").toString());
        
        // Track event
        System.out.println("Event tracked: " + eventType + " for user: " + userId);
        
        return ResponseEntity.ok(Map.of("status", "Event tracked successfully"));
    }
    
    @GetMapping("/experiments/{experimentId}/variant")
    @Operation(summary = "Get A/B test variant for user")
    public ResponseEntity<Map<String, String>> getVariant(
            @PathVariable String experimentId,
            @RequestParam Long userId) {
        
        String variant = abTestingService.getVariant(experimentId, userId);
        return ResponseEntity.ok(Map.of(
            "experimentId", experimentId,
            "userId", userId.toString(),
            "variant", variant
        ));
    }
    
    @PostMapping("/experiments/{experimentId}/conversion")
    @Operation(summary = "Track A/B test conversion")
    public ResponseEntity<Map<String, String>> trackConversion(
            @PathVariable String experimentId,
            @RequestParam Long userId,
            @RequestParam String event) {
        
        abTestingService.trackConversion(experimentId, userId, event);
        return ResponseEntity.ok(Map.of("status", "Conversion tracked"));
    }
    
    @GetMapping("/experiments/{experimentId}/results")
    @Operation(summary = "Get A/B test results")
    public ResponseEntity<Map<String, Object>> getExperimentResults(@PathVariable String experimentId) {
        return ResponseEntity.ok(abTestingService.getExperimentResults(experimentId));
    }
    
    @GetMapping("/dashboard")
    @Operation(summary = "Get analytics dashboard data")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        return ResponseEntity.ok(Map.of(
            "totalUsers", 1250,
            "activeUsers", 890,
            "pageViews", 15670,
            "conversionRate", 3.2,
            "bounceRate", 45.6,
            "avgSessionDuration", "4m 32s",
            "topPages", List.of(
                Map.of("page", "/products", "views", 5670),
                Map.of("page", "/", "views", 3450),
                Map.of("page", "/cart", "views", 2340)
            ),
            "realtimeUsers", 45,
            "timestamp", LocalDateTime.now()
        ));
    }
}