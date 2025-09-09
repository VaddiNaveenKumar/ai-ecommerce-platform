package com.ecommerce.core.repository;

import com.ecommerce.core.entity.Order;
import com.ecommerce.core.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    List<Payment> findByOrder(Order order);
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByStatus(String status);
    
    @Query("SELECT p FROM Payment p WHERE p.fraudRiskScore > :threshold")
    List<Payment> findHighRiskPayments(Double threshold);
    
    @Query("SELECT p FROM Payment p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    List<Payment> findPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}