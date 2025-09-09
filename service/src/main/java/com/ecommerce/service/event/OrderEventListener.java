package com.ecommerce.service.event;

import com.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
    
    private final NotificationService notificationService;
    
    @KafkaListener(topics = "order-updates", groupId = "ecommerce-group")
    public void handleOrderUpdate(Map<String, Object> orderEvent) {
        try {
            Long orderId = ((Number) orderEvent.get("orderId")).longValue();
            String status = (String) orderEvent.get("status");
            
            log.info("Processing order update: orderId={}, status={}", orderId, status);
            
            notificationService.sendOrderUpdateNotification(orderId, status);
            
        } catch (Exception e) {
            log.error("Error processing order update event", e);
        }
    }
    
    @KafkaListener(topics = "push-notifications", groupId = "ecommerce-group")
    public void handlePushNotification(Map<String, Object> notificationEvent) {
        try {
            Long userId = ((Number) notificationEvent.get("userId")).longValue();
            String title = (String) notificationEvent.get("title");
            String message = (String) notificationEvent.get("message");
            
            log.info("Processing push notification: userId={}, title={}", userId, title);
            
            // Process push notification
            
        } catch (Exception e) {
            log.error("Error processing push notification event", e);
        }
    }
}