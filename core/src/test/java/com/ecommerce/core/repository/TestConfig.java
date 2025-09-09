package com.ecommerce.core.repository;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.ecommerce.core.entity")
@EnableJpaRepositories(basePackages = "com.ecommerce.core.repository", 
                      excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(
                          pattern = ".*SearchRepository"))
public class TestConfig {
}