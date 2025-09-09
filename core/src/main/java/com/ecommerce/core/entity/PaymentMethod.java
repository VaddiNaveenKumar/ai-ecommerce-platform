package com.ecommerce.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "payment_methods")
@EqualsAndHashCode(callSuper = true)
public class PaymentMethod extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @NotBlank
    private String type; // CARD, UPI, WALLET
    
    private String cardNumber; // Encrypted/Tokenized
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;
    
    private String upiId;
    private String walletProvider;
    
    private boolean isDefault = false;
    private boolean active = true;
}