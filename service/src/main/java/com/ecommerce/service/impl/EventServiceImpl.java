package com.ecommerce.service.impl;

import com.ecommerce.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Override
    public void publishOrderCreated(Long orderId, Long userId, Map<String, Object> orderData) {
        Map<String, Object> event = Map.of(
            "eventType", "ORDER_CREATED",
            "orderId", orderId,
            "userId", userId,
            "orderData", orderData,
            "timestamp", LocalDateTime.now()
        );
        
        kafkaTemplate.send("order-events", event);
        log.info("Published ORDER_CREATED event for order: {}", orderId);
    }
    
    @Override
    public void publishOrderStatusChanged(Long orderId, String oldStatus, String newStatus) {
        Map<String, Object> event = Map.of(
            "eventType", "ORDER_STATUS_CHANGED",
            "orderId", orderId,
            "oldStatus", oldStatus,
            "newStatus", newStatus,
            "timestamp", LocalDateTime.now()
        );
        
        kafkaTemplate.send("order-events", event);
        log.info("Published ORDER_STATUS_CHANGED event for order: {} from {} to {}", orderId, oldStatus, newStatus);
    }
    
    @Override
    public void publishInventoryUpdated(Long productId, Integer oldStock, Integer newStock) {
        Map<String, Object> event = Map.of(
            "eventType", "INVENTORY_UPDATED",
            "productId", productId,
            "oldStock", oldStock,
            "newStock", newStock,
            "timestamp", LocalDateTime.now()
        );
        
        kafkaTemplate.send("inventory-events", event);
        log.info("Published INVENTORY_UPDATED event for product: {} from {} to {}", productId, oldStock, newStock);
    }
    
    @Override
    public void publishUserRegistered(Long userId, String email) {
        Map<String, Object> event = Map.of(
            "eventType", "USER_REGISTERED",
            "userId", userId,
            "email", email,
            "timestamp", LocalDateTime.now()
        );
        
        kafkaTemplate.send("user-events", event);
        log.info("Published USER_REGISTERED event for user: {}", userId);
    }
    
    @Override
    public void publishPaymentProcessed(Long orderId, String paymentStatus, Double amount) {
        Map<String, Object> event = Map.of(
            "eventType", "PAYMENT_PROCESSED",
            "orderId", orderId,
            "paymentStatus", paymentStatus,
            "amount", amount,
            "timestamp", LocalDateTime.now()
        );
        
        kafkaTemplate.send("payment-events", event);
        log.info("Published PAYMENT_PROCESSED event for order: {} with status: {}", orderId, paymentStatus);
    }
}