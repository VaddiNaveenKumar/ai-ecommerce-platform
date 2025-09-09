package com.ecommerce.service.impl;

import com.ecommerce.core.entity.User;
import com.ecommerce.core.enums.UserRole;
import com.ecommerce.core.repository.UserRepository;
import com.ecommerce.service.EmailService;
import com.ecommerce.service.UserService;
import com.ecommerce.service.dto.UserProfileDto;
import com.ecommerce.service.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    
    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        // Check if user already exists
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPhone(registrationDto.getPhone());
        user.setRole(UserRole.CUSTOMER);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        
        User savedUser = userRepository.save(user);
        
        // Send welcome email asynchronously
        sendWelcomeEmailAsync(user.getEmail(), user.getUsername());
        
        return savedUser;
    }
    
    @Async
    private void sendWelcomeEmailAsync(String email, String username) {
        try {
            emailService.sendWelcomeEmail(email, username);
        } catch (Exception e) {
            System.out.println("Failed to send welcome email: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public User updateProfile(Long userId, UserProfileDto profileDto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setPhone(profileDto.getPhone());
        user.setEmail(profileDto.getEmail());
        
        return userRepository.save(user);
    }
    
    @Override
    public void enableMfa(Long userId, String secret) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setMfaEnabled(true);
        user.setMfaSecret(secret);
        userRepository.save(user);
    }
    
    @Override
    public void disableMfa(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setMfaEnabled(false);
        user.setMfaSecret(null);
        userRepository.save(user);
    }
    
    @Override
    public String generateReferralCode(Long userId) {
        return "REF" + userId + System.currentTimeMillis();
    }
    
    @Override
    public void updateLastLogin(Long userId, String deviceInfo) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLastLogin(LocalDateTime.now());
        user.setLastLoginDevice(deviceInfo);
        userRepository.save(user);
    }
    
    @Override
    public void unlockAccount(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAccountNonLocked(true);
        userRepository.save(user);
    }
    
    @Override
    public void lockAccount(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAccountNonLocked(false);
        userRepository.save(user);
    }
}