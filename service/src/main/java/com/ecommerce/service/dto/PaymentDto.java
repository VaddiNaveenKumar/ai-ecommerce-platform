package com.ecommerce.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    
    @NotNull
    private Long orderId;
    
    @NotNull
    private String paymentMethod; // CARD, UPI, WALLET, COD
    
    @NotNull
    private BigDecimal amount;
    
    private String cardNumber;
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;
    
    private String upiId;
    private String walletProvider;
    
    private String billingAddress;
}