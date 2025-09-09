package com.ecommerce.core.repository;

import com.ecommerce.core.entity.User;
import com.ecommerce.core.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByReferralCode(String referralCode);
    
    List<User> findByRole(UserRole role);
    List<User> findByEnabledTrue();
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.enabled = true")
    List<User> findActiveUsersByRole(UserRole role);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}