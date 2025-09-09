package com.ecommerce.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "reviews")
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Min(1) @Max(5)
    private Integer rating;
    
    @Column(length = 2000)
    private String comment;
    
    private boolean verified = false;
    
    // AI-powered fields
    private String sentimentScore;
    private boolean aiDetectedFake = false;
    private String aiSummary;
    
    private Integer helpfulCount = 0;
    private Integer reportCount = 0;
}