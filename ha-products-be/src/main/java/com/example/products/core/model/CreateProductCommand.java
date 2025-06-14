package com.example.products.core.model;

public record CreateProductCommand(
        String manufacturer,
        String substanceName,
        String brandName,
        String productNumbers) {
}