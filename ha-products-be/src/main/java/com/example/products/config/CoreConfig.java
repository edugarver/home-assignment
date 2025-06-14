package com.example.products.config;

import com.example.products.core.IdGenerator;
import com.example.products.core.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@Import(ProductService.class)
@Configuration
public class CoreConfig {

    @Bean
    IdGenerator idGenerator() {
        return () -> UUID.randomUUID().toString();
    }
}
