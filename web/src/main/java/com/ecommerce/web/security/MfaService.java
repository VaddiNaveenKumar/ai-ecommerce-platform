package com.ecommerce.web.security;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MfaService {
    
    private final Map<String, String> mfaCodes = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();
    
    public String generateMfaSecret() {
        byte[] secretBytes = new byte[20];
        random.nextBytes(secretBytes);
        return Base64.getEncoder().encodeToString(secretBytes);
    }
    
    public String generateMfaCode(String secret) {
        // Simple 6-digit code generation
        int code = 100000 + random.nextInt(900000);
        String codeStr = String.valueOf(code);
        
        // Store code with expiration (5 minutes)
        mfaCodes.put(secret, codeStr);
        
        // Remove code after 5 minutes
        new Thread(() -> {
            try {
                Thread.sleep(300000); // 5 minutes
                mfaCodes.remove(secret);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        return codeStr;
    }
    
    public boolean validateMfaCode(String secret, String code) {
        String storedCode = mfaCodes.get(secret);
        if (storedCode != null && storedCode.equals(code)) {
            mfaCodes.remove(secret); // Remove after successful validation
            return true;
        }
        return false;
    }
    
    public String getQrCodeUrl(String secret, String userEmail) {
        String issuer = "AI E-commerce Platform";
        String accountName = userEmail;
        
        return String.format(
            "otpauth://totp/%s:%s?secret=%s&issuer=%s",
            issuer, accountName, secret, issuer
        );
    }
}