package com.ecommerce.web.controller;

import com.ecommerce.web.service.CDNService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.Map;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
@Tag(name = "Performance", description = "Performance monitoring and optimization")
public class PerformanceController {
    
    private final CDNService cdnService;
    
    @GetMapping("/metrics")
    @Operation(summary = "Get performance metrics")
    public ResponseEntity<Map<String, Object>> getPerformanceMetrics() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        
        return ResponseEntity.ok(Map.of(
            "uptime", runtimeBean.getUptime(),
            "startTime", runtimeBean.getStartTime(),
            "memory", Map.of(
                "heap", Map.of(
                    "used", memoryBean.getHeapMemoryUsage().getUsed(),
                    "max", memoryBean.getHeapMemoryUsage().getMax(),
                    "committed", memoryBean.getHeapMemoryUsage().getCommitted()
                ),
                "nonHeap", Map.of(
                    "used", memoryBean.getNonHeapMemoryUsage().getUsed(),
                    "max", memoryBean.getNonHeapMemoryUsage().getMax(),
                    "committed", memoryBean.getNonHeapMemoryUsage().getCommitted()
                )
            ),
            "processors", Runtime.getRuntime().availableProcessors(),
            "threads", Thread.activeCount()
        ));
    }
    
    @GetMapping("/cdn/stats")
    @Operation(summary = "Get CDN statistics")
    public ResponseEntity<Map<String, Object>> getCDNStats() {
        return ResponseEntity.ok(cdnService.getCDNStats());
    }
    
    @PostMapping("/cdn/invalidate")
    @Operation(summary = "Invalidate CDN cache")
    public ResponseEntity<Map<String, String>> invalidateCDNCache(@RequestParam String path) {
        cdnService.invalidateCache(path);
        return ResponseEntity.ok(Map.of("message", "CDN cache invalidated for: " + path));
    }
    
    @GetMapping("/database/stats")
    @Operation(summary = "Get database performance stats")
    public ResponseEntity<Map<String, Object>> getDatabaseStats() {
        // Simulate database performance metrics
        return ResponseEntity.ok(Map.of(
            "connectionPool", Map.of(
                "active", 5,
                "idle", 15,
                "max", 20,
                "min", 5
            ),
            "queryPerformance", Map.of(
                "avgResponseTime", "45ms",
                "slowQueries", 3,
                "totalQueries", 15670
            ),
            "cacheHitRate", 92.5,
            "indexUsage", 98.2
        ));
    }
    
    @GetMapping("/cache/stats")
    @Operation(summary = "Get cache performance stats")
    public ResponseEntity<Map<String, Object>> getCacheStats() {
        return ResponseEntity.ok(Map.of(
            "redis", Map.of(
                "hitRate", 89.5,
                "missRate", 10.5,
                "evictions", 125,
                "keyCount", 5670,
                "memoryUsage", "256MB"
            ),
            "applicationCache", Map.of(
                "hitRate", 95.2,
                "size", 1250,
                "maxSize", 10000
            )
        ));
    }
}