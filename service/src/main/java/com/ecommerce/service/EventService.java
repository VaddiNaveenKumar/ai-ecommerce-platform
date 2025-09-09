package com.ecommerce.service;

import java.util.Map;

public interface EventService {
    void publishOrderCreated(Long orderId, Long userId, Map<String, Object> orderData);
    void publishOrderStatusChanged(Long orderId, String oldStatus, String newStatus);
    void publishInventoryUpdated(Long productId, Integer oldStock, Integer newStock);
    void publishUserRegistered(Long userId, String email);
    void publishPaymentProcessed(Long orderId, String paymentStatus, Double amount);
}