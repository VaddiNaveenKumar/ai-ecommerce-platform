package com.ecommerce.web.controller;

import com.ecommerce.service.UserService;
import com.ecommerce.web.security.MfaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mfa")
@RequiredArgsConstructor
@Tag(name = "MFA", description = "Multi-factor authentication")
public class MfaController {
    
    private final MfaService mfaService;
    private final UserService userService;
    
    @PostMapping("/enable")
    @Operation(summary = "Enable MFA for user")
    public ResponseEntity<Map<String, String>> enableMfa(Authentication authentication) {
        String username = authentication.getName();
        
        // Generate MFA secret
        String secret = mfaService.generateMfaSecret();
        
        // Get user ID (simplified - in real app, get from user service)
        Long userId = 1L; // This should be retrieved from the authenticated user
        
        // Enable MFA for user
        userService.enableMfa(userId, secret);
        
        // Generate QR code URL
        String qrCodeUrl = mfaService.getQrCodeUrl(secret, username);
        
        return ResponseEntity.ok(Map.of(
            "secret", secret,
            "qrCodeUrl", qrCodeUrl,
            "message", "MFA enabled successfully. Scan the QR code with your authenticator app."
        ));
    }
    
    @PostMapping("/disable")
    @Operation(summary = "Disable MFA for user")
    public ResponseEntity<Map<String, String>> disableMfa(Authentication authentication) {
        // Get user ID (simplified)
        Long userId = 1L;
        
        userService.disableMfa(userId);
        
        return ResponseEntity.ok(Map.of("message", "MFA disabled successfully"));
    }
    
    @PostMapping("/verify")
    @Operation(summary = "Verify MFA code")
    public ResponseEntity<Map<String, Object>> verifyMfaCode(
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        
        String code = request.get("code");
        String secret = request.get("secret");
        
        boolean isValid = mfaService.validateMfaCode(secret, code);
        
        return ResponseEntity.ok(Map.of(
            "valid", isValid,
            "message", isValid ? "MFA code verified successfully" : "Invalid MFA code"
        ));
    }
    
    @PostMapping("/generate-code")
    @Operation(summary = "Generate MFA code for testing")
    public ResponseEntity<Map<String, String>> generateCode(@RequestBody Map<String, String> request) {
        String secret = request.get("secret");
        String code = mfaService.generateMfaCode(secret);
        
        return ResponseEntity.ok(Map.of(
            "code", code,
            "message", "MFA code generated (valid for 5 minutes)"
        ));
    }
}