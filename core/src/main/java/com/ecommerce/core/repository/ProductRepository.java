package com.ecommerce.core.repository;

import com.ecommerce.core.entity.Product;
import com.ecommerce.core.entity.Category;
import com.ecommerce.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByActiveTrue();
    List<Product> findByFeaturedTrue();
    List<Product> findByCategory(Category category);
    List<Product> findBySeller(User seller);
    
    Page<Product> findByActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    Page<Product> findByActiveTrueAndFeaturedTrueOrderByRatingDesc(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.basePrice BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.rating >= :minRating ORDER BY p.rating DESC")
    List<Product> findByMinimumRating(Double minRating);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND (p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR p.aiGeneratedTags LIKE %:keyword%)")
    Page<Product> searchProducts(String keyword, Pageable pageable);
    
    List<Product> findByStockQuantityLessThan(Integer threshold);
}