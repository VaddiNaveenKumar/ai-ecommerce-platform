package com.ecommerce.core.repository;

import com.ecommerce.core.entity.User;
import com.ecommerce.core.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(UserRole.CUSTOMER);
        entityManager.persistAndFlush(user);
        
        // When
        var foundUser = userRepository.findByUsername("testuser");
        
        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }
    
    @Test
    void existsByEmail_ShouldReturnTrue_WhenEmailExists() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(UserRole.CUSTOMER);
        entityManager.persistAndFlush(user);
        
        // When
        boolean exists = userRepository.existsByEmail("test@example.com");
        
        // Then
        assertThat(exists).isTrue();
    }
}