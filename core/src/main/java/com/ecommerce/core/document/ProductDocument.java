package com.ecommerce.core.document;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDocument {
    
    @Id
    private String id;
    
    private String name;
    private String description;
    private String brand;
    private String category;
    private BigDecimal price;
    private Integer stockQuantity;
    private Double rating;
    private Integer reviewCount;
    private Boolean featured;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}