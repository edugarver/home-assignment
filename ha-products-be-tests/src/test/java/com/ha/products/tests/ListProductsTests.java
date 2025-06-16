package com.ha.products.tests;

import com.ha.products.BaseApiTest;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.greaterThan;

public class ListProductsTests extends BaseApiTest {

  @Tag("tc1") @Tag("smoke") @Tag("regression")
  @Test
  @DisplayName("List all products")
  @Description("Verify that the API returns a list of all products with status code 200")
  public void listAllProductsTest() {
    // Retrieve the list of products and validate the response schema
    requestSpec
        .when()
          .get(LIST_PRODUCTS_ENDPOINT)
        .then()
          .statusCode(200)
          .body("size()", greaterThan(0))
          .body(matchesJsonSchemaInClasspath("schemas/products-list-schema.json"));
  }

}