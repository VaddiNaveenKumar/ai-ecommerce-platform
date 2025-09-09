package com.ecommerce.web.controller;

import com.ecommerce.core.entity.User;
import com.ecommerce.service.UserService;
import com.ecommerce.service.dto.UserProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User profile and account management")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/profile")
    @Operation(summary = "Get user profile")
    public ResponseEntity<User> getProfile(Authentication auth) {
        return userService.findByUsername(auth.getName())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/profile")
    @Operation(summary = "Update user profile")
    public ResponseEntity<User> updateProfile(
            @Valid @RequestBody UserProfileDto profileDto,
            Authentication auth) {
        
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        User updatedUser = userService.updateProfile(user.getId(), profileDto);
        return ResponseEntity.ok(updatedUser);
    }
    
    @PostMapping("/mfa/enable")
    @Operation(summary = "Enable MFA")
    public ResponseEntity<Map<String, String>> enableMfa(
            @RequestParam String secret,
            Authentication auth) {
        
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        userService.enableMfa(user.getId(), secret);
        return ResponseEntity.ok(Map.of("message", "MFA enabled successfully"));
    }
    
    @PostMapping("/mfa/disable")
    @Operation(summary = "Disable MFA")
    public ResponseEntity<Map<String, String>> disableMfa(Authentication auth) {
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        userService.disableMfa(user.getId());
        return ResponseEntity.ok(Map.of("message", "MFA disabled successfully"));
    }
    
    @GetMapping("/referral-code")
    @Operation(summary = "Get referral code")
    public ResponseEntity<Map<String, String>> getReferralCode(Authentication auth) {
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        String code = userService.generateReferralCode(user.getId());
        return ResponseEntity.ok(Map.of("referralCode", code));
    }
}