
package com.ha.products.utils;

import com.ha.products.BaseApiTest;
import com.ha.products.models.CreateProductRequest;
import com.ha.products.models.CreateProductResponse;

public class ProductTestUtils extends BaseApiTest {

  private static final String CREATE_PRODUCT_ENDPOINT = "/create";

  public static CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
    return requestSpec
        .body(createProductRequest)
        .when()
        .post(CREATE_PRODUCT_ENDPOINT)
        .then()
        .extract().as(CreateProductResponse.class);
  }
}
