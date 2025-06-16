package com.ha.products.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductResponse {

  private boolean success;
  private String errorMessage;
  private String applicationNumber;

}
