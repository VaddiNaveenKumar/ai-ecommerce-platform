package com.ecommerce.web.controller;

import com.ecommerce.core.entity.Review;
import com.ecommerce.core.entity.User;
import com.ecommerce.service.ReviewService;
import com.ecommerce.service.UserService;
import com.ecommerce.service.dto.ReviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Product review and rating management")
public class ReviewController {
    
    private final ReviewService reviewService;
    private final UserService userService;
    
    @PostMapping
    @Operation(summary = "Create review")
    public ResponseEntity<Review> createReview(
            @Valid @RequestBody ReviewDto reviewDto,
            Authentication auth) {
        
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        reviewDto.setUserId(user.getId());
        Review review = reviewService.createReview(reviewDto);
        return ResponseEntity.ok(review);
    }
    
    @GetMapping("/product/{productId}")
    @Operation(summary = "Get product reviews")
    public ResponseEntity<Page<Review>> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<Review> reviews = reviewService.getProductReviews(productId, PageRequest.of(page, size));
        return ResponseEntity.ok(reviews);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user reviews")
    public ResponseEntity<Page<Review>> getUserReviews(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<Review> reviews = reviewService.getUserReviews(userId, PageRequest.of(page, size));
        return ResponseEntity.ok(reviews);
    }
    
    @PostMapping("/{id}/helpful")
    @Operation(summary = "Mark review as helpful")
    public ResponseEntity<Map<String, String>> markHelpful(
            @PathVariable Long id,
            Authentication auth) {
        
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        reviewService.markReviewHelpful(id, user.getId());
        return ResponseEntity.ok(Map.of("message", "Review marked as helpful"));
    }
    
    @PostMapping("/{id}/report")
    @Operation(summary = "Report review")
    public ResponseEntity<Map<String, String>> reportReview(
            @PathVariable Long id,
            @RequestParam String reason,
            Authentication auth) {
        
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        reviewService.reportReview(id, user.getId(), reason);
        return ResponseEntity.ok(Map.of("message", "Review reported successfully"));
    }
    
    @GetMapping("/analytics/product/{productId}")
    @Operation(summary = "Get review analytics")
    public ResponseEntity<Map<String, Object>> getReviewAnalytics(@PathVariable Long productId) {
        Map<String, Object> analytics = reviewService.getReviewAnalytics(productId);
        return ResponseEntity.ok(analytics);
    }
    
    @GetMapping("/{id}/fake-check")
    @Operation(summary = "Check if review is fake")
    public ResponseEntity<Map<String, Object>> checkFakeReview(@PathVariable Long id) {
        boolean isFake = reviewService.isReviewFake(id);
        return ResponseEntity.ok(Map.of(
            "isFake", isFake,
            "confidence", isFake ? 0.85 : 0.15
        ));
    }
}