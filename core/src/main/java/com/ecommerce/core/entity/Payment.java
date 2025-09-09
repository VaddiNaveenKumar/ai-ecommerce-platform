package com.ecommerce.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "payments")
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @NotNull
    private String paymentMethod; // CARD, UPI, WALLET, COD, EMI
    
    @NotNull
    private BigDecimal amount;
    
    @NotNull
    private String status; // PENDING, SUCCESS, FAILED, REFUNDED
    
    private String transactionId;
    private String gatewayResponse;
    
    // AI-powered fraud detection
    private Double fraudRiskScore;
    private String aiRiskAssessment;
}