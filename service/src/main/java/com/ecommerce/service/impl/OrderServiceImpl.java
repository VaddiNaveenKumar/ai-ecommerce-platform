package com.ecommerce.service.impl;

import com.ecommerce.core.entity.*;
import com.ecommerce.core.enums.OrderStatus;
import com.ecommerce.core.repository.*;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    @Override
    public Order createOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Order order = new Order();
        order.setOrderNumber("ORD-" + System.currentTimeMillis());
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderDto.OrderItemDto itemDto : orderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setUnitPrice(itemDto.getUnitPrice());
            orderItem.setTotalPrice(itemDto.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
            
            totalAmount = totalAmount.add(orderItem.getTotalPrice());
        }
        
        order.setTotalAmount(totalAmount);
        order.setEstimatedDelivery(LocalDateTime.now().plusDays(3));
        
        // AI predictions (placeholder)
        order.setDeliveryRiskScore(0.1);
        order.setAiDeliveryPrediction("Standard delivery expected");
        
        return orderRepository.save(order);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Order> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Order> findByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatusOrderByCreatedAtDesc(status, pageable);
    }
    
    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        
        if (status == OrderStatus.DELIVERED) {
            order.setActualDelivery(LocalDateTime.now());
        }
        
        return orderRepository.save(order);
    }
    
    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.CONFIRMED) {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Cannot cancel order in current status");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public double calculateDeliveryRisk(Long orderId) {
        return 0.1; // Low risk placeholder
    }
    
    @Override
    @Transactional(readOnly = true)
    public String predictDeliveryTime(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        return "Expected delivery: " + order.getEstimatedDelivery().toLocalDate();
    }
}