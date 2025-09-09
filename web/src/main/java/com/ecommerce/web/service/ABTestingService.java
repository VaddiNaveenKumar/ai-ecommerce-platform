package com.ecommerce.web.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ABTestingService {
    
    private final Map<String, String> experiments = new ConcurrentHashMap<>();
    
    public String getVariant(String experimentId, Long userId) {
        String key = experimentId + "_" + userId;
        return experiments.computeIfAbsent(key, k -> {
            // Simple A/B split based on user ID
            return userId % 2 == 0 ? "A" : "B";
        });
    }
    
    public void trackConversion(String experimentId, Long userId, String event) {
        String variant = getVariant(experimentId, userId);
        // Log conversion for analytics
        System.out.println("Conversion tracked: " + experimentId + " - " + variant + " - " + event);
    }
    
    public Map<String, Object> getExperimentResults(String experimentId) {
        // Simulate experiment results
        return Map.of(
            "experimentId", experimentId,
            "variantA", Map.of("users", 500, "conversions", 45, "rate", 9.0),
            "variantB", Map.of("users", 500, "conversions", 52, "rate", 10.4),
            "winner", "B",
            "confidence", 85.2
        );
    }
}