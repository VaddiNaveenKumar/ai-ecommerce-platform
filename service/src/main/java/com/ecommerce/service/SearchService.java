package com.ecommerce.service;

import com.ecommerce.core.document.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SearchService {
    Page<ProductDocument> searchProducts(String query, Pageable pageable);
    Page<ProductDocument> searchByCategory(String category, Pageable pageable);
    Page<ProductDocument> searchByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    List<String> getSearchSuggestions(String query);
    Map<String, Long> getSearchAnalytics();
    void indexProduct(ProductDocument product);
    void deleteProductFromIndex(String productId);
}