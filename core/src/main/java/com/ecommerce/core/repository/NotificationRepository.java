package com.ecommerce.core.repository;

import com.ecommerce.core.entity.Notification;
import com.ecommerce.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByUser(User user);
    List<Notification> findByUserAndReadFalse(User user);
    List<Notification> findByUserAndType(User user, String type);
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    
    void deleteByUserAndReadTrue(User user);
}