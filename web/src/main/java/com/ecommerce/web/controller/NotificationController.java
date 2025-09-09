package com.ecommerce.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Real-time notification system")
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/broadcast")
    @Operation(summary = "Send broadcast notification")
    public Map<String, String> broadcastNotification(@RequestBody Map<String, String> notification) {
        Map<String, Object> message = Map.of(
            "type", notification.get("type"),
            "message", notification.get("message"),
            "timestamp", LocalDateTime.now()
        );
        
        messagingTemplate.convertAndSend("/topic/notifications", message);
        return Map.of("status", "Notification sent successfully");
    }

    @PostMapping("/user/{userId}")
    @Operation(summary = "Send notification to specific user")
    public Map<String, String> sendUserNotification(
            @PathVariable Long userId, 
            @RequestBody Map<String, String> notification) {
        
        Map<String, Object> message = Map.of(
            "type", notification.get("type"),
            "message", notification.get("message"),
            "timestamp", LocalDateTime.now()
        );
        
        messagingTemplate.convertAndSendToUser(
            userId.toString(), 
            "/queue/notifications", 
            message
        );
        
        return Map.of("status", "User notification sent successfully");
    }

    @MessageMapping("/notifications")
    @SendTo("/topic/notifications")
    public Map<String, Object> handleNotification(Map<String, String> notification) {
        return Map.of(
            "type", notification.get("type"),
            "message", notification.get("message"),
            "timestamp", LocalDateTime.now()
        );
    }
}