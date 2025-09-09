package com.ecommerce.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    
    @NotNull
    private Long userId;
    
    @NotNull
    private Long shippingAddressId;
    
    @NotNull
    private Long billingAddressId;
    
    @NotNull
    private List<OrderItemDto> items;
    
    private String couponCode;
    private String paymentMethod;
    
    @Data
    public static class OrderItemDto {
        @NotNull
        private Long productId;
        
        private Long variantId;
        
        @NotNull
        private Integer quantity;
        
        @NotNull
        private BigDecimal unitPrice;
    }
}