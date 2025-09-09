package com.ecommerce.service;

import com.ecommerce.core.entity.Notification;
import com.ecommerce.core.entity.User;

import java.util.List;

public interface NotificationService {
    
    void sendOrderUpdateNotification(Long orderId, String status);
    void sendPromotionNotification(Long userId, String message);
    void sendWelcomeNotification(Long userId);
    
    List<Notification> getUserNotifications(Long userId, boolean unreadOnly);
    void markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
    
    void sendEmail(String to, String subject, String body);
    void sendSMS(String phone, String message);
    void sendPushNotification(Long userId, String title, String message);
}