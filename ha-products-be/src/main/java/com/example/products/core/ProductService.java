package com.example.products.core;

import com.example.products.core.model.CreateProductCommand;
import com.example.products.core.model.Product;
import com.example.products.core.model.SearchQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Validator validator;
    private final IdGenerator idGenerator;

    public Product createProduct(CreateProductCommand command) {
        final Product product = new Product(
                idGenerator.generateUniqueId(),
                command.manufacturer(),
                command.substanceName(),
                command.brandName(),
                command.productNumbers()
        );
        validateOrThrow(product);
        return productRepository.createProduct(product);
    }

    public List<Product> listProducts() {
        return productRepository.listProducts();
    }

    public List<Product> searchProducts(SearchQuery query) {
        return productRepository.searchProducts(query);
    }

    private <T> void validateOrThrow(T model) {
        final Set<ConstraintViolation<T>> violations = validator.validate(model);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Error occurred while validating the %s, errors count: %d".formatted(
                    model.getClass().getSimpleName(),
                    violations.size()
            ), violations);
        }
    }
}
