package com.ecommerce.core.repository;

import com.ecommerce.core.document.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductSearchRepository extends JpaRepository<ProductDocument, String> {
    
    Page<ProductDocument> findByNameContainingOrDescriptionContaining(
            String name, String description, Pageable pageable);
    
    Page<ProductDocument> findByBrand(String brand, Pageable pageable);
    
    Page<ProductDocument> findByCategory(String category, Pageable pageable);
    
    Page<ProductDocument> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    Page<ProductDocument> findByFeaturedTrue(Pageable pageable);
    
    List<ProductDocument> findTop10ByOrderByRatingDesc();
    
    List<ProductDocument> findByTagsContaining(String tag);
}