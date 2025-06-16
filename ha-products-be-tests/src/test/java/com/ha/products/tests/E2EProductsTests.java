package com.ha.products.tests;

import com.ha.products.BaseApiTest;
import com.ha.products.models.CreateProductRequest;
import com.ha.products.models.CreateProductResponse;
import com.ha.products.models.Product;
import com.ha.products.models.SearchProductRequest;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class E2EProductsTests extends BaseApiTest {

  @Tag("tc18") @Tag("e2e")
  @Test
  @DisplayName("Create a product and search for it")
  @Description("This test creates a new product, checks the product list, and searches for the created product.")
  public void e2eCreateAndSearchProductTest() {
    // Step 1: Create a product and extract the response to use in subsequent steps
    String manufacturer = "Manufacturer_" + System.currentTimeMillis();
    String substanceName = "SubstanceName_" + System.currentTimeMillis();
    String brandName = "BrandName_" + System.currentTimeMillis();
    String productNumbers = "ProductNumbers_" + System.currentTimeMillis();

    // Create the product using the request specification and validate the response schema
    CreateProductResponse response = requestSpec
        .body(CreateProductRequest.builder()
            .manufacturer(manufacturer)
            .substanceName(substanceName)
            .brandName(brandName)
            .productNumbers(productNumbers)
            .build())
      .when()
        .post("/create")
      .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/product-create-schema.json"))
        .extract().as(CreateProductResponse.class);

    // Build the product object to be used in the search validation
    Product createdProduct = Product.builder()
        .applicationNumber(response.getApplicationNumber())
        .manufacturer(manufacturer)
        .substanceName(substanceName)
        .brandName(brandName)
        .productNumbers(productNumbers)
        .build();

    // Step 2: List products and verify the created product is present
    requestSpec
      .when()
        .get(LIST_PRODUCTS_ENDPOINT)
      .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/products-list-schema.json"))
        .body("$", hasSize(greaterThan(0)))
        .body("findAll { it.applicationNumber == '" + createdProduct.getApplicationNumber() + "' " +
                "&& it.manufacturer == '" + createdProduct.getManufacturer() + "' " +
                "&& it.substanceName == '" + createdProduct.getSubstanceName() + "' " +
                "&& it.brandName == '" + createdProduct.getBrandName() + "' " +
                "&& it.productNumbers == '" + createdProduct.getProductNumbers() + "' }.size()",
            equalTo(1));

    // Step 3: Search for the created product
    // Verify that the response contains the product created in the previous step
    // Verify that every item in the response has the expected manufacturer and brand name
    requestSpec
        .body(SearchProductRequest.builder()
            .manufacturer(manufacturer)
            .brandName(brandName)
            .build())
      .when()
        .post("/search")
      .then()
        .statusCode(200)
        .body("$", hasSize(greaterThan(0)))
        .body("applicationNumber.findAll { it == '" + createdProduct.getApplicationNumber() + "' }", hasSize(1))
        .body("manufacturer", everyItem(equalTo(createdProduct.getManufacturer())))
        .body("brandName", everyItem(equalTo(createdProduct.getBrandName())));;;
  }
}
