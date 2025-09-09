package com.ecommerce.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "product_images")
@EqualsAndHashCode(callSuper = true)
public class ProductImage extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @NotBlank
    private String imageUrl;
    
    private String altText;
    private String aiGeneratedAltText;
    
    private boolean isPrimary = false;
    private Integer sortOrder = 0;
}