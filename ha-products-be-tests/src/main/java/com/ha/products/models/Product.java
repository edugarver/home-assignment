package com.ha.products.models;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Product {
    private String applicationNumber;
    private String manufacturer;
    private String substanceName;
    private String brandName;
    private String productNumbers;
}