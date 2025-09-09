package com.ecommerce.service;

import com.ecommerce.core.entity.Review;
import com.ecommerce.service.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ReviewService {
    
    Review createReview(ReviewDto reviewDto);
    Page<Review> getProductReviews(Long productId, Pageable pageable);
    Page<Review> getUserReviews(Long userId, Pageable pageable);
    
    void markReviewHelpful(Long reviewId, Long userId);
    void reportReview(Long reviewId, Long userId, String reason);
    
    Map<String, Object> getReviewAnalytics(Long productId);
    boolean isReviewFake(Long reviewId);
}