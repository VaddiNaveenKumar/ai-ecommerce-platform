package com.ecommerce.web.controller;

import com.ecommerce.core.entity.User;
import com.ecommerce.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return userRepository.findByUsername(username)
            .map(user -> {
                Map<String, Object> response = new HashMap<>();
                response.put("username", user.getUsername());
                response.put("email", user.getEmail());
                response.put("enabled", user.isEnabled());
                response.put("accountNonLocked", user.isAccountNonLocked());
                response.put("role", user.getRole());
                response.put("passwordHash", user.getPassword().substring(0, Math.min(10, user.getPassword().length())) + "...");
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/password-check")
    public ResponseEntity<?> checkPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        
        return userRepository.findByUsername(username)
            .map(user -> {
                boolean matches = passwordEncoder.matches(password, user.getPassword());
                Map<String, Object> response = new HashMap<>();
                response.put("username", username);
                response.put("passwordMatches", matches);
                response.put("storedPassword", user.getPassword());
                response.put("providedPassword", password);
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}