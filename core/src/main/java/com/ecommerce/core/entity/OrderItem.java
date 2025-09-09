package com.ecommerce.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;
    
    @Min(1)
    private Integer quantity;
    
    @NotNull
    private BigDecimal unitPrice;
    
    @NotNull
    private BigDecimal totalPrice;
    
    private BigDecimal discountAmount = BigDecimal.ZERO;
}