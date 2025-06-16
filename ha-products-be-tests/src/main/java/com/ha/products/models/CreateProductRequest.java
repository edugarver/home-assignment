package com.ha.products.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductRequest {
  private String manufacturer;
  private String substanceName;
  private String brandName;
  private String productNumbers;
}