package com.ecommerce.service;

import com.ecommerce.core.entity.Order;
import com.ecommerce.core.enums.OrderStatus;
import com.ecommerce.service.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    
    Order createOrder(OrderDto orderDto);
    Optional<Order> findById(Long id);
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByUserId(Long userId);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    
    Order updateOrderStatus(Long orderId, OrderStatus status);
    void cancelOrder(Long orderId);
    
    double calculateDeliveryRisk(Long orderId);
    String predictDeliveryTime(Long orderId);
}