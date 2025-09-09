package com.ecommerce.service.impl;

import com.ecommerce.core.entity.Notification;
import com.ecommerce.core.entity.User;
import com.ecommerce.core.repository.UserRepository;
import com.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Override
    public void sendOrderUpdateNotification(Long orderId, String status) {
        kafkaTemplate.send("order-updates", Map.of(
            "orderId", orderId,
            "status", status,
            "timestamp", System.currentTimeMillis()
        ));
    }
    
    @Override
    public void sendPromotionNotification(Long userId, String message) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            sendEmail(user.getEmail(), "Special Promotion", message);
        }
    }
    
    @Override
    public void sendWelcomeNotification(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            sendEmail(user.getEmail(), "Welcome!", "Welcome to our platform!");
        }
    }
    
    @Override
    public List<Notification> getUserNotifications(Long userId, boolean unreadOnly) {
        return List.of(); // Placeholder
    }
    
    @Override
    public void markAsRead(Long notificationId) {
        // Implementation placeholder
    }
    
    @Override
    public void markAllAsRead(Long userId) {
        // Implementation placeholder
    }
    
    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            // Log error
        }
    }
    
    @Override
    public void sendSMS(String phone, String message) {
        // SMS implementation placeholder
    }
    
    @Override
    public void sendPushNotification(Long userId, String title, String message) {
        kafkaTemplate.send("push-notifications", Map.of(
            "userId", userId,
            "title", title,
            "message", message
        ));
    }
}