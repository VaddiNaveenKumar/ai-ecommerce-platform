package com.ecommerce.service.impl;

import com.ecommerce.core.document.ProductDocument;
import com.ecommerce.core.repository.ProductSearchRepository;
import com.ecommerce.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    
    private final ProductSearchRepository searchRepository;
    private final Map<String, Long> searchStats = new ConcurrentHashMap<>();
    
    @Override
    public Page<ProductDocument> searchProducts(String query, Pageable pageable) {
        // Track search query
        searchStats.merge(query.toLowerCase(), 1L, Long::sum);
        
        return searchRepository.findByNameContainingOrDescriptionContaining(
                query, query, pageable);
    }
    
    @Override
    public Page<ProductDocument> searchByCategory(String category, Pageable pageable) {
        return searchRepository.findByCategory(category, pageable);
    }
    
    @Override
    public Page<ProductDocument> searchByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return searchRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    }
    
    @Override
    public List<String> getSearchSuggestions(String query) {
        // Simple suggestion implementation
        return List.of(
            query + " pro",
            query + " max",
            query + " mini",
            "best " + query,
            query + " deals"
        );
    }
    
    @Override
    public Map<String, Long> getSearchAnalytics() {
        return Map.copyOf(searchStats);
    }
    
    @Override
    public void indexProduct(ProductDocument product) {
        searchRepository.save(product);
    }
    
    @Override
    public void deleteProductFromIndex(String productId) {
        searchRepository.deleteById(productId);
    }
}