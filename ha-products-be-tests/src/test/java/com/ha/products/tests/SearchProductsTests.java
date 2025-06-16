package com.ha.products.tests;

import com.ha.products.BaseApiTest;
import com.ha.products.models.Product;
import com.ha.products.models.SearchProductRequest;
import com.ha.products.utils.ProductTestUtils;
import com.ha.products.models.CreateProductRequest;
import com.ha.products.models.CreateProductResponse;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class SearchProductsTests extends BaseApiTest {

  @Tag("tc3")
  @Tag("smoke")
  @Tag("regression")
  @Test
  @DisplayName("Search for a product")
  @Description("This test searches for a product by creating it first and validating the response schema.")
  public void searchProductTest() {
    String manufacturer = "Manufacturer_" + System.currentTimeMillis();
    String substanceName = "SubstanceName_" + System.currentTimeMillis();
    String brandName = "BrandName_" + System.currentTimeMillis();
    String productNumbers = "ProductNumbers_" + System.currentTimeMillis();

    // Create the product using the utility method and save the response to use in the search response
    CreateProductRequest createProductRequest = CreateProductRequest.builder()
        .manufacturer(manufacturer)
        .substanceName(substanceName)
        .brandName(brandName)
        .productNumbers(productNumbers)
        .build();
    CreateProductResponse createProductResponse = ProductTestUtils.createProduct(createProductRequest);

    // Build the product object to be used in the search validation
    Product product = Product.builder()
        .applicationNumber(createProductResponse.getApplicationNumber())
        .manufacturer(manufacturer)
        .substanceName(substanceName)
        .brandName(brandName)
        .productNumbers(productNumbers)
        .build();

    // Search for the product by manufacturer and brand name
    // Verify that the response contains the product created in the previous step
    // Verify that every item in the response has the expected manufacturer and brand name
    requestSpec
        .body(SearchProductRequest.builder()
            .manufacturer(manufacturer)
            .brandName(brandName)
            .build())
        .when()
        .post(SEARCH_PRODUCTS_ENDPOINT)
        .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/products-list-schema.json"))
        .body("$", hasSize(greaterThan(0)))
        .body("applicationNumber.findAll { it == '" + product.getApplicationNumber() + "' }", hasSize(1))
        .body("manufacturer", everyItem(equalTo(product.getManufacturer())))
        .body("brandName", everyItem(equalTo(product.getBrandName())));
  }

  @Tag("tc11")
  @Tag("regression")
  @Test
  @DisplayName("Search for a product with empty request body")
  @Description("This test searches for a product with an empty request body and expects all products to be returned.")
  public void searchProductWithEmptyBodyTest() {
    // Search for a product with an empty request body
    // Verify that all products are returned in the response
    requestSpec
        .body("{}") // Empty body
        .when()
        .post(SEARCH_PRODUCTS_ENDPOINT)
        .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/products-list-schema.json"))
        .body("$", hasSize(greaterThan(0))); // Assuming there are products in the database
  }

  @Tag("tc13")
  @Tag("regression")
  @Test
  @DisplayName("Search for a product with only manufacturer")
  @Description("This test searches for a product by manufacturer only and expects all products with the given manufacturer to be returned.")
  public void searchProductWithOnlyManufacturerTest() {
    String manufacturer = "Manufacturer_" + System.currentTimeMillis();
    String substanceName = "SubstanceName_" + System.currentTimeMillis();
    String brandName = "BrandName_" + System.currentTimeMillis();
    String productNumbers = "ProductNumbers_" + System.currentTimeMillis();

    // Create a product to ensure there are products with the given manufacturer
    CreateProductRequest createProductRequest = CreateProductRequest.builder()
        .manufacturer(manufacturer)
        .substanceName(substanceName)
        .brandName(brandName)
        .productNumbers(productNumbers)
        .build();
    ProductTestUtils.createProduct(createProductRequest);

    // Search for a product with only manufacturer
    requestSpec
        .body(SearchProductRequest.builder()
            .manufacturer(manufacturer)
            .build())
        .when()
        .post(SEARCH_PRODUCTS_ENDPOINT)
        .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/products-list-schema.json"))
        .body("$", hasSize(greaterThan(0)))
        .body("manufacturer", everyItem(equalTo(manufacturer)));
  }

  @Tag("tc14")
  @Tag("regression")
  @Test
  @DisplayName("Search for a product with only brand name")
  @Description("This test searches for a product by brand name only and expects all products with the given brand name to be returned.")
  public void searchProductWithOnlyBrandNameTest() {
    String brandName = "BrandName_" + System.currentTimeMillis();
    String manufacturer = "Manufacturer_" + System.currentTimeMillis();
    String substanceName = "SubstanceName_" + System.currentTimeMillis();
    String productNumbers = "ProductNumbers_" + System.currentTimeMillis();

    // Create a product to ensure there are products with the given brand name
    CreateProductRequest createProductRequest = CreateProductRequest.builder()
        .manufacturer(manufacturer)
        .substanceName(substanceName)
        .brandName(brandName)
        .productNumbers(productNumbers)
        .build();
    ProductTestUtils.createProduct(createProductRequest);

    // Search for a product with only brand name
    requestSpec
        .body(SearchProductRequest.builder()
            .brandName(brandName)
            .build())
        .when()
        .post(SEARCH_PRODUCTS_ENDPOINT)
        .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/products-list-schema.json"))
        .body("$", hasSize(greaterThan(0)))
        .body("brandName", everyItem(equalTo(brandName)));
  }

  @Tag("tc15")
  @Tag("regression")
  @Test
  @DisplayName("Search for a product with non-existing manufacturer")
  @Description("This test searches for a product with a non-existing manufacturer and expects an empty array in the response.")
  public void searchProductWithNonExistingManufacturerTest() {
    String nonExistingManufacturer = "NonExistingManufacturer_" + System.currentTimeMillis();

    // Search for a product with a non-existing manufacturer
    requestSpec
        .body(SearchProductRequest.builder()
            .manufacturer(nonExistingManufacturer)
            .build())
        .when()
        .post(SEARCH_PRODUCTS_ENDPOINT)
        .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/products-list-schema.json"))
        .body("$", hasSize(0)); // Expecting an empty array
  }

  @Tag("tc16")
  @Tag("regression")
  @Test
  @DisplayName("Search for a product with non-existing brand name")
  @Description("This test searches for a product with a non-existing brand name and expects an empty array in the response.")
  public void searchProductWithNonExistingBrandNameTest() {
    String nonExistingBrandName = "NonExistingBrandName_" + System.currentTimeMillis();

    // Search for a product with a non-existing brand name
    requestSpec
        .body(SearchProductRequest.builder()
            .brandName(nonExistingBrandName)
            .build())
        .when()
        .post(SEARCH_PRODUCTS_ENDPOINT)
        .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath("schemas/products-list-schema.json"))
        .body("$", hasSize(0)); // Expecting an empty array
  }

  @Tag("tc17")
  @Tag("regression")
  @Test
  @Disabled("Some types different than String are supported and they shouldn't be")
  @DisplayName("Type validation for search product request")
  @Description("This test validates that the search product request only accepts String types for manufacturer and brand name.")
  public void searchProductTypeValidationTest() {
    // Attempt to search for a product with an invalid type for manufacturer
    requestSpec
        .body("{\"manufacturer\":12345}")
        .when()
          .post(SEARCH_PRODUCTS_ENDPOINT)
        .then()
          .statusCode(400) // Expecting a 400 Bad Request due to type validation error
          .body("errorMessage", containsString("Invalid type for manufacturer"));

    // Attempt to search for a product with an invalid type for brand name
    requestSpec
        .body("{\"brandName\":12345}")
        .when()
        .post(SEARCH_PRODUCTS_ENDPOINT)
        .then()
        .statusCode(400) // Expecting a 400 Bad Request due to type validation error
        .body("errorMessage", containsString("Invalid type for brand name"));
  }

}
