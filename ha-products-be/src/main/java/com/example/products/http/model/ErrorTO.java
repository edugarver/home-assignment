package com.example.products.http.model;

import jakarta.validation.ConstraintViolationException;
import lombok.Value;

import java.util.List;

@Value
public class ErrorTO {
    private final String errorMessage;

    public boolean isSuccess() {
        return false;
    }

    public static ErrorTO from(ConstraintViolationException ex) {
        final List<String> stringifiedViolations = ex.getConstraintViolations()
                .stream()
                .map(constraintViolation -> "'%s' -> %s".formatted(constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
                .toList();
        return new ErrorTO("%s, violations found: %s".formatted(ex.getMessage(), stringifiedViolations));
    }

}
