package com.example.products.adapters.mongo.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@FieldNameConstants
@Document(collection = "products")
public final class ProductEntity {
    @Indexed(unique = true)
    private final String applicationNumber;
    private final String manufacturer;
    private final String substanceName;
    private final String brandName;
    private final String productNumbers;
}