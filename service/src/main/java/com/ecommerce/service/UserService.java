package com.ecommerce.service;

import com.ecommerce.core.entity.User;
import com.ecommerce.service.dto.UserRegistrationDto;
import com.ecommerce.service.dto.UserProfileDto;

import java.util.Optional;

public interface UserService {
    
    User registerUser(UserRegistrationDto registrationDto);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    
    User updateProfile(Long userId, UserProfileDto profileDto);
    void enableMfa(Long userId, String secret);
    void disableMfa(Long userId);
    
    void lockAccount(Long userId);
    void unlockAccount(Long userId);
    
    void updateLastLogin(Long userId, String deviceInfo);
    String generateReferralCode(Long userId);
}