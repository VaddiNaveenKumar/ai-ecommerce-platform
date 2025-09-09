package com.ecommerce.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product_variants")
@EqualsAndHashCode(callSuper = true)
public class ProductVariant extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    private String size;
    private String color;
    private String weight;
    private String material;
    
    @Column(unique = true)
    private String variantSku;
    
    private BigDecimal additionalPrice = BigDecimal.ZERO;
    private Integer stockQuantity = 0;
    
    private boolean active = true;
}