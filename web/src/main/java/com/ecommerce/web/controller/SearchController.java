package com.ecommerce.web.controller;

import com.ecommerce.core.document.ProductDocument;
import com.ecommerce.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "Advanced product search with Elasticsearch")
public class SearchController {
    
    private final SearchService searchService;
    
    @GetMapping
    @Operation(summary = "Search products with advanced filters")
    public ResponseEntity<Page<ProductDocument>> searchProducts(
            @RequestParam String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        PageRequest pageRequest = PageRequest.of(page, size);
        
        if (category != null) {
            Page<ProductDocument> results = searchService.searchByCategory(category, pageRequest);
            return ResponseEntity.ok(results);
        }
        
        if (minPrice != null && maxPrice != null) {
            Page<ProductDocument> results = searchService.searchByPriceRange(minPrice, maxPrice, pageRequest);
            return ResponseEntity.ok(results);
        }
        
        Page<ProductDocument> results = searchService.searchProducts(q, pageRequest);
        return ResponseEntity.ok(results);
    }
    
    @GetMapping("/suggestions")
    @Operation(summary = "Get search suggestions")
    public ResponseEntity<List<String>> getSearchSuggestions(@RequestParam String q) {
        List<String> suggestions = searchService.getSearchSuggestions(q);
        return ResponseEntity.ok(suggestions);
    }
    
    @GetMapping("/popular")
    @Operation(summary = "Get popular search terms")
    public ResponseEntity<Map<String, Long>> getPopularSearches() {
        Map<String, Long> analytics = searchService.getSearchAnalytics();
        return ResponseEntity.ok(analytics);
    }
    
    @GetMapping("/analytics")
    @Operation(summary = "Get search analytics")
    public ResponseEntity<Map<String, Object>> getSearchAnalytics() {
        Map<String, Long> searchStats = searchService.getSearchAnalytics();
        
        return ResponseEntity.ok(Map.of(
            "totalSearches", searchStats.values().stream().mapToLong(Long::longValue).sum(),
            "uniqueQueries", searchStats.size(),
            "topQueries", searchStats.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .toList(),
            "searchTrends", Map.of(
                "today", 1250,
                "yesterday", 1180,
                "thisWeek", 8900,
                "lastWeek", 8200
            )
        ));
    }
}