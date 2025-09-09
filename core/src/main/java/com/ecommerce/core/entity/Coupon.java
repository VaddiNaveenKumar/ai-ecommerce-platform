package com.ecommerce.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "coupons")
@EqualsAndHashCode(callSuper = true)
public class Coupon extends BaseEntity {
    
    @NotBlank
    @Column(unique = true)
    private String code;
    
    @NotBlank
    private String name;
    
    private String description;
    
    private String discountType; // PERCENTAGE, FIXED_AMOUNT
    private BigDecimal discountValue;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscountAmount;
    
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    
    private Integer usageLimit;
    private Integer usedCount = 0;
    private Integer userUsageLimit = 1;
    
    private boolean active = true;
    private boolean firstTimeUserOnly = false;
    
    // AI-powered fields
    private String targetUserSegment;
    private Double aiEffectivenessScore;
}