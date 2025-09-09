package com.ecommerce.core.repository;

import com.ecommerce.core.entity.Product;
import com.ecommerce.core.entity.Review;
import com.ecommerce.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    Page<Review> findByProduct(Product product, Pageable pageable);
    Page<Review> findByUser(User user, Pageable pageable);
    List<Review> findByProductAndVerifiedTrue(Product product);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product = :product")
    Double getAverageRatingByProduct(Product product);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.product = :product AND r.rating = :rating")
    Long countByProductAndRating(Product product, Integer rating);
    
    List<Review> findByAiDetectedFakeTrue();
}