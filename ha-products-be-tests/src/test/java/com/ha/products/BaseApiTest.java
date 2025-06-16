package com.ha.products;

import com.ha.products.config.ApiConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseApiTest {
  protected static RequestSpecification requestSpec;
  protected static final String CREATE_PRODUCT_ENDPOINT = "/create";
  protected static final String LIST_PRODUCTS_ENDPOINT = "/list";
  protected static final String SEARCH_PRODUCTS_ENDPOINT = "/search";

  @BeforeAll
  public static void init() {
    ApiConfig.setup();
  }

  @BeforeEach
  public void setUp() {
    requestSpec = RestAssured.given()
        .spec(ApiConfig.getRequestSpec())
        .filter(new AllureRestAssured());
  }
}