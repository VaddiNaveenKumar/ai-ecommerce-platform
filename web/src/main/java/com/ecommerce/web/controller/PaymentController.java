package com.ecommerce.web.controller;

import com.ecommerce.core.entity.Payment;
import com.ecommerce.service.PaymentService;
import com.ecommerce.service.dto.PaymentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment processing and management")
public class PaymentController {
    
    private final PaymentService paymentService;
    
    @PostMapping("/process")
    @Operation(summary = "Process payment")
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody PaymentDto paymentDto) {
        Payment payment = paymentService.processPayment(paymentDto);
        return ResponseEntity.ok(payment);
    }
    
    @PostMapping("/{id}/refund")
    @Operation(summary = "Refund payment")
    public ResponseEntity<Payment> refundPayment(
            @PathVariable Long id,
            @RequestParam String reason) {
        
        Payment payment = paymentService.refundPayment(id, reason);
        return ResponseEntity.ok(payment);
    }
    
    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get order payments")
    public ResponseEntity<List<Payment>> getOrderPayments(@PathVariable Long orderId) {
        List<Payment> payments = paymentService.getOrderPayments(orderId);
        return ResponseEntity.ok(payments);
    }
    
    @GetMapping("/transaction/{transactionId}")
    @Operation(summary = "Get payment by transaction ID")
    public ResponseEntity<Payment> getPaymentByTransactionId(@PathVariable String transactionId) {
        Payment payment = paymentService.getPaymentByTransactionId(transactionId);
        return payment != null ? ResponseEntity.ok(payment) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/validate")
    @Operation(summary = "Validate payment method")
    public ResponseEntity<Map<String, Object>> validatePaymentMethod(
            @RequestParam String paymentMethod,
            @RequestParam String details) {
        
        boolean valid = paymentService.validatePaymentMethod(paymentMethod, details);
        return ResponseEntity.ok(Map.of(
            "valid", valid,
            "message", valid ? "Payment method is valid" : "Invalid payment method"
        ));
    }
    
    @PostMapping("/fraud-check")
    @Operation(summary = "Check fraud risk")
    public ResponseEntity<Map<String, Object>> checkFraudRisk(@Valid @RequestBody PaymentDto paymentDto) {
        double riskScore = paymentService.calculateFraudRisk(paymentDto);
        
        return ResponseEntity.ok(Map.of(
            "riskScore", riskScore,
            "riskLevel", riskScore > 0.7 ? "HIGH" : riskScore > 0.3 ? "MEDIUM" : "LOW",
            "recommendation", riskScore > 0.7 ? "BLOCK" : "APPROVE"
        ));
    }
}