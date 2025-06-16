package com.ha.products.models;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class SearchProductRequest {
  private String manufacturer;
  private String brandName;
}
