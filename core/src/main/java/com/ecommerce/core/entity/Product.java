package com.ecommerce.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "products")
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    
    @NotBlank
    private String name;
    
    @Column(length = 2000)
    private String description;
    
    @NotBlank
    @Column(unique = true)
    private String sku;
    
    private String upc;
    private String brand;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;
    
    @NotNull
    private BigDecimal basePrice;
    
    private BigDecimal discountPrice;
    private Integer discountPercentage;
    
    private Integer stockQuantity = 0;
    private Integer reservedQuantity = 0;
    
    private Double rating = 0.0;
    private Integer reviewCount = 0;
    
    private boolean active = true;
    private boolean featured = false;
    
    // AI-powered fields
    @Column(length = 1000)
    private String aiGeneratedTags;
    
    @Column(length = 2000)
    private String seoDescription;
    
    private Double aiRecommendationScore;
    private String sustainabilityScore;
    
    private LocalDateTime expiryDate;
    private boolean returnable = true;
    private boolean replaceable = true;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductVariant> variants;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductImage> images;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Review> reviews;
}