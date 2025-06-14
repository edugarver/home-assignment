package com.example.products.http.model;

public record CreateProductTO(
        String manufacturer,
        String substanceName,
        String brandName,
        String productNumbers) {
}