package com.ecommerce.web.controller;

import com.ecommerce.core.entity.Category;
import com.ecommerce.core.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Product category management")
public class CategoryController {
    
    private final CategoryRepository categoryRepository;
    
    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findActiveCategoriesOrderBySortOrder();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/root")
    @Operation(summary = "Get root categories")
    public ResponseEntity<List<Category>> getRootCategories() {
        List<Category> categories = categoryRepository.findByParentIsNull();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}/children")
    @Operation(summary = "Get category children")
    public ResponseEntity<List<Category>> getCategoryChildren(@PathVariable Long id) {
        Category parent = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        
        List<Category> children = categoryRepository.findByParent(parent);
        return ResponseEntity.ok(children);
    }
}