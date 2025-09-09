package com.ecommerce.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "addresses")
@EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @NotBlank
    private String fullName;
    
    @NotBlank
    private String addressLine1;
    
    private String addressLine2;
    
    @NotBlank
    private String city;
    
    @NotBlank
    private String state;
    
    @NotBlank
    private String country;
    
    @NotBlank
    private String zipCode;
    
    private String phone;
    
    private boolean isDefault = false;
    private String addressType; // HOME, WORK, OTHER
}