package com.ecommerce.service;

public interface EmailService {
    void sendWelcomeEmail(String to, String username);
    void sendOrderConfirmation(String to, String orderNumber);
    void sendPasswordReset(String to, String resetToken);
    void sendOrderStatusUpdate(String to, String orderNumber, String status);
}