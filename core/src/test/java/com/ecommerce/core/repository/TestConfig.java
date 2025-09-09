package com.ecommerce.core.repository;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.ecommerce.core.entity")
@EnableJpaRepositories("com.ecommerce.core.repository")
public class TestConfig {
}