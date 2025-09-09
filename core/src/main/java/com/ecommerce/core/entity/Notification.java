package com.ecommerce.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "notifications")
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @NotBlank
    private String title;
    
    @Column(length = 1000)
    private String message;
    
    private String type; // ORDER_UPDATE, PROMOTION, SYSTEM
    private String channel; // EMAIL, SMS, PUSH, IN_APP
    
    private boolean read = false;
    private boolean sent = false;
    
    private String referenceId; // Order ID, Product ID, etc.
    private String referenceType; // ORDER, PRODUCT, USER
}