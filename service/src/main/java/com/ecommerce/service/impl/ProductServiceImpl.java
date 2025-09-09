package com.ecommerce.service.impl;

import com.ecommerce.core.entity.Product;
import com.ecommerce.core.entity.User;
import com.ecommerce.core.repository.ProductRepository;
import com.ecommerce.core.repository.UserRepository;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    @Override
    public Product createProduct(ProductDto productDto) {
        User seller = userRepository.findById(productDto.getSellerId())
            .orElseThrow(() -> new RuntimeException("Seller not found"));
        
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setBrand(productDto.getBrand());
        product.setBasePrice(productDto.getBasePrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setActive(productDto.isActive());
        product.setFeatured(productDto.isFeatured());
        product.setSeller(seller);
        
        // AI metadata (placeholder)
        product.setAiGeneratedTags("electronics, gadget");
        product.setSeoDescription("High quality product");
        product.setAiRecommendationScore(0.8);
        
        return productRepository.save(product);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findByActiveTrueOrderByCreatedAtDesc(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchProducts(keyword, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findByPriceRange(minPrice, maxPrice, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> findFeaturedProducts() {
        return productRepository.findByFeaturedTrue();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> getRecommendations(Long userId, int limit) {
        // Simple recommendation logic
        return productRepository.findByFeaturedTrue().stream().limit(limit).toList();
    }
    
    @Override
    public Product updateStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.setStockQuantity(quantity);
        return productRepository.save(product);
    }
    
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.setActive(false);
        productRepository.save(product);
    }
}