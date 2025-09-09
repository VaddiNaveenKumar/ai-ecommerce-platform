package com.ecommerce.service;

import com.ecommerce.core.entity.Payment;
import com.ecommerce.service.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    
    Payment processPayment(PaymentDto paymentDto);
    Payment refundPayment(Long paymentId, String reason);
    
    List<Payment> getOrderPayments(Long orderId);
    Payment getPaymentByTransactionId(String transactionId);
    
    double calculateFraudRisk(PaymentDto paymentDto);
    boolean validatePaymentMethod(String paymentMethod, String details);
}