package com.ecommerce.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    
    @NotBlank
    private String name;
    
    private String description;
    
    @NotBlank
    private String sku;
    
    private String brand;
    
    @NotNull
    private Long categoryId;
    
    @NotNull
    private Long sellerId;
    
    @NotNull
    private BigDecimal basePrice;
    
    private Integer stockQuantity = 0;
    private boolean active = true;
    private boolean featured = false;
}