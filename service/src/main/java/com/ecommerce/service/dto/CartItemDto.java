package com.ecommerce.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDto {
    
    @NotNull
    private Long productId;
    
    private Long variantId;
    
    @NotNull
    @Min(1)
    private Integer quantity;
    
    private boolean savedForLater = false;
}