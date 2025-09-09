package com.ecommerce.web.controller;

import com.ecommerce.core.entity.User;
import com.ecommerce.service.UserService;
import com.ecommerce.service.dto.UserRegistrationDto;
import com.ecommerce.web.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication and registration")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    
    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            User user = userService.registerUser(registrationDto);
            return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "userId", user.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");
            
            System.out.println("DEBUG: Login attempt for username: " + username);
            System.out.println("DEBUG: Password provided: " + password);
            
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String token = jwtUtil.generateToken(userDetails);
            
            System.out.println("DEBUG: Login successful for: " + username);
            
            return ResponseEntity.ok(Map.of(
                "token", token,
                "type", "Bearer",
                "username", username
            ));
        } catch (Exception e) {
            System.out.println("DEBUG: Login failed for username: " + loginRequest.get("username"));
            System.out.println("DEBUG: Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials: " + e.getMessage()));
        }
    }
    
    @PostMapping("/oauth2/success")
    @Operation(summary = "OAuth2 success callback")
    public ResponseEntity<?> oauth2Success() {
        // Handle OAuth2 success
        return ResponseEntity.ok(Map.of("message", "OAuth2 login successful"));
    }
}