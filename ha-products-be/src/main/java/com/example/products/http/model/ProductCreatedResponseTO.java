package com.example.products.http.model;

public record ProductCreatedResponseTO(
        boolean success,
        String errorMessage,
        String applicationNumber
) {

    public static ProductCreatedResponseTO success(String applicationNumber) {
        return new ProductCreatedResponseTO(true, null, applicationNumber);
    }

}
