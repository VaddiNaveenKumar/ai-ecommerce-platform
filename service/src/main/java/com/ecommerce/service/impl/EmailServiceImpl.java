package com.ecommerce.service.impl;

import com.ecommerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    
    private final JavaMailSender mailSender;
    
    @Override
    public void sendWelcomeEmail(String to, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Welcome to AI E-commerce Platform!");
            message.setText(String.format(
                "Hi %s,\n\n" +
                "Welcome to our AI-powered e-commerce platform!\n\n" +
                "You can now:\n" +
                "- Browse our AI-curated product catalog\n" +
                "- Get personalized recommendations\n" +
                "- Enjoy dynamic pricing\n\n" +
                "Happy shopping!\n\n" +
                "Best regards,\n" +
                "AI E-commerce Team", username));
            
            mailSender.send(message);
            log.info("Welcome email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send welcome email to: {}", to, e);
        }
    }
    
    @Override
    public void sendOrderConfirmation(String to, String orderNumber) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Order Confirmation - " + orderNumber);
            message.setText(String.format(
                "Your order %s has been confirmed!\n\n" +
                "Our AI system is now processing your order and will provide delivery predictions soon.\n\n" +
                "You can track your order status in your account.\n\n" +
                "Thank you for shopping with us!\n\n" +
                "Best regards,\n" +
                "AI E-commerce Team", orderNumber));
            
            mailSender.send(message);
            log.info("Order confirmation email sent to: {} for order: {}", to, orderNumber);
        } catch (Exception e) {
            log.error("Failed to send order confirmation email to: {}", to, e);
        }
    }
    
    @Override
    public void sendPasswordReset(String to, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Password Reset Request");
            message.setText(String.format(
                "You requested a password reset.\n\n" +
                "Your reset token is: %s\n\n" +
                "This token will expire in 1 hour.\n\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "AI E-commerce Team", resetToken));
            
            mailSender.send(message);
            log.info("Password reset email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", to, e);
        }
    }
    
    @Override
    public void sendOrderStatusUpdate(String to, String orderNumber, String status) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Order Status Update - " + orderNumber);
            message.setText(String.format(
                "Your order %s status has been updated to: %s\n\n" +
                "You can track your order in your account for real-time updates.\n\n" +
                "Thank you for shopping with us!\n\n" +
                "Best regards,\n" +
                "AI E-commerce Team", orderNumber, status));
            
            mailSender.send(message);
            log.info("Order status update email sent to: {} for order: {}", to, orderNumber);
        } catch (Exception e) {
            log.error("Failed to send order status update email to: {}", to, e);
        }
    }
}