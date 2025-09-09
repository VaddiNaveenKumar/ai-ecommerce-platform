package com.ecommerce.core.repository;

import com.ecommerce.core.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    List<Category> findByActiveTrue();
    List<Category> findByParentIsNull();
    List<Category> findByParent(Category parent);
    
    @Query("SELECT c FROM Category c WHERE c.active = true ORDER BY c.sortOrder")
    List<Category> findActiveCategoriesOrderBySortOrder();
}