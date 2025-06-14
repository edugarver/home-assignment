package com.example.products.adapters.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(ProductMongoRepository.class)
@Configuration
class MongoConfig {
}
