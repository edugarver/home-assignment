package com.ha.products.tests;

import com.ha.products.BaseApiTest;
import com.ha.products.models.CreateProductRequest;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class CreateProductsTests extends BaseApiTest {

  @Tag("tc2")
  @Tag("smoke")
  @Tag("regression")
  @Test
  @DisplayName("Create a product")
  @Description("This test creates a new product and validates the response schema.")
  public void createProductTest() {
  // Create a product with unique values to avoid possible conflicts
    requestSpec
        .body(CreateProductRequest.builder()
            .manufacturer("Manufacturer_" + System.currentTimeMillis())
            .substanceName("SubstanceName_" + System.currentTimeMillis())
            .brandName("BrandName_" + System.currentTimeMillis())
            .productNumbers("ProductNumbers_" + System.currentTimeMillis())
            .build())
        .when()
          .post(CREATE_PRODUCT_ENDPOINT)
        .then()
          .statusCode(200)
          .body(matchesJsonSchemaInClasspath("schemas/product-create-schema.json"))
          .body("applicationNumber", notNullValue())
          .body("success", is(true))
          .body("errorMessage", nullValue());
  }

  @Tag("tc4")
  @Tag("regression")
  @Test
  @DisplayName("Create a product with only required properties")
  @Description("This test creates a product with only the required properties and validates the response schema.")
  public void createProductWithRequiredPropertiesTest() {
    // Create a product with only the required properties
    requestSpec
        .body(CreateProductRequest.builder()
            .manufacturer("Manufacturer_" + System.currentTimeMillis())
            .substanceName("SubstanceName_" + System.currentTimeMillis())
            .build())
        .when()
          .post(CREATE_PRODUCT_ENDPOINT)
        .then()
          .statusCode(200)
          .body(matchesJsonSchemaInClasspath("schemas/product-create-schema.json"))
          .body("applicationNumber", notNullValue())
          .body("success", is(true))
          .body("errorMessage", nullValue());
  }

  @Tag("tc5")
  @Tag("regression")
  @Test
  @Disabled("Disabled due to known issue with empty manufacturer")
  @DisplayName("Create a product with an empty manufacturer")
  @Description("This test attempts to create a product with an empty manufacturer and expects a validation error.")
  public void createProductWithEmptyManufacturerTest() {
    // Attempt to create a product with an empty manufacturer
    requestSpec
        .body(CreateProductRequest.builder()
            .manufacturer("")
            .substanceName("SubstanceName_" + System.currentTimeMillis())
            .brandName("BrandName_" + System.currentTimeMillis())
            .productNumbers("ProductNumbers_" + System.currentTimeMillis())
            .build())
        .when()
          .post(CREATE_PRODUCT_ENDPOINT)
        .then()
          .statusCode(400)
          .body("errorMessage", equalTo("Error occurred while validating the Product, errors count: 1, violations found: ['manufacturer' -> must not be blank]"));
  }

  @Tag("tc6")
  @Tag("regression")
  @Test
  @DisplayName("Create a product with an empty substance name")
  @Description("This test attempts to create a product with an empty substance name and expects a validation error.")
  public void createProductWithEmptySubstanceNameTest() {
    // Attempt to create a product with an empty substance name
    requestSpec
        .body(CreateProductRequest.builder()
            .manufacturer("Manufacturer_" + System.currentTimeMillis())
            .substanceName("")
            .brandName("BrandName_" + System.currentTimeMillis())
            .productNumbers("ProductNumbers_" + System.currentTimeMillis())
            .build())
        .when()
          .post(CREATE_PRODUCT_ENDPOINT)
        .then()
          .statusCode(400)
          .body("errorMessage", equalTo("Error occurred while validating the Product, errors count: 1, violations found: ['substanceName' -> must not be blank]"));
  }

  @Tag("tc7")
  @Tag("regression")
  @Test
  @Disabled("Some types different than String are supported and they shouldn't be")
  @DisplayName("Type validation for create product request")
  @Description("This test validates that the create product request only accepts String types for manufacturer, substance name, brand name, and product numbers.")
  public void createProductTypeValidationTest() {
    requestSpec
        .body("{\"manufacturer\":12345,\"substanceName\":67890,\"brandName\":11121,\"productNumbers\":31415}")
        .when()
          .post(CREATE_PRODUCT_ENDPOINT)
        .then()
          .statusCode(400) // Expecting a 400 Bad Request due to type validation error
          .body("errorMessage", containsString("Invalid type for manufacturer"));
  }

  @Tag("tc8")
  @Tag("regression")
  @Test
  @DisplayName("Create a product with a manufacturer longer than 50 characters")
  @Description("This test attempts to create a product with a manufacturer longer than 50 characters and expects a validation error.")
  public void createProductWithLongManufacturerTest() {
    // Attempt to create a product with a manufacturer longer than 50 characters
    String longManufacturer = "A".repeat(51); // 51 characters long
    requestSpec
        .body(CreateProductRequest.builder()
            .manufacturer(longManufacturer)
            .substanceName("SubstanceName_" + System.currentTimeMillis())
            .brandName("BrandName_" + System.currentTimeMillis())
            .productNumbers("ProductNumbers_" + System.currentTimeMillis())
            .build())
        .when()
          .post(CREATE_PRODUCT_ENDPOINT)
        .then()
          .statusCode(400)
          .body("errorMessage", equalTo("Error occurred while validating the Product, errors count: 1, violations found: ['manufacturer' -> length must be between 0 and 50]"));
  }

  @Tag("tc9")
  @Tag("regression")
  @Test
  @Disabled("The API allows you to create a product with a substance name longer than 50 characters and it should not")
  @DisplayName("Create a product with a substance name longer than 50 characters")
  @Description("This test attempts to create a product with a substance name longer than 50 characters and expects a validation error.")
  public void createProductWithLongSubstanceNameTest() {
    // Attempt to create a product with a substance name longer than 50 characters
    String longSubstanceName = "A".repeat(51); // 51 characters long
    requestSpec
        .body(CreateProductRequest.builder()
            .manufacturer("Manufacturer_" + System.currentTimeMillis())
            .substanceName(longSubstanceName)
            .brandName("BrandName_" + System.currentTimeMillis())
            .productNumbers("ProductNumbers_" + System.currentTimeMillis())
            .build())
        .when()
          .post(CREATE_PRODUCT_ENDPOINT)
        .then()
          .statusCode(400)
          .body("errorMessage", equalTo("Error occurred while validating the Product, errors count: 1, violations found: ['substanceName' -> length must be between 0 and 50]"));
  }

  @Tag("tc10")
  @Tag("regression")
  @Test
  @DisplayName("Create a product with a brand name longer than 50 characters")
  @Description("This test attempts to create a product with a brand name longer than 50 characters and expects a validation error.")
  public void createProductWithLongBrandNameTest() {
    // Attempt to create a product with a brand name longer than 50 characters
    String longBrandName = "A".repeat(51); // 51 characters long
    requestSpec
        .body(CreateProductRequest.builder()
            .manufacturer("Manufacturer_" + System.currentTimeMillis())
            .substanceName("SubstanceName_" + System.currentTimeMillis())
            .brandName(longBrandName)
            .productNumbers("ProductNumbers_" + System.currentTimeMillis())
            .build())
        .when()
          .post(CREATE_PRODUCT_ENDPOINT)
        .then()
          .statusCode(400)
          .body("errorMessage", equalTo("Error occurred while validating the Product, errors count: 1, violations found: ['brandName' -> length must be between 0 and 50]"));
  }

  @Tag("tc11")
  @Tag("regression")
  @Test
  @DisplayName("Create a product with a product number longer than 1000 characters")
  @Description("This test attempts to create a product with a product number longer than 1000 characters and expects a validation error.")
  public void createProductWithLongProductNumberTest() {
    // Attempt to create a product with a product number longer than 1000 characters
    String longProductNumber = "A".repeat(1001); // 1001 characters long
    requestSpec
        .body(CreateProductRequest.builder()
            .manufacturer("Manufacturer_" + System.currentTimeMillis())
            .substanceName("SubstanceName_" + System.currentTimeMillis())
            .brandName("BrandName_" + System.currentTimeMillis())
            .productNumbers(longProductNumber)
            .build())
        .when()
          .post(CREATE_PRODUCT_ENDPOINT)
        .then()
          .statusCode(400)
          .body("errorMessage", equalTo("Error occurred while validating the Product, errors count: 1, violations found: ['productNumbers' -> length must be between 0 and 1000]"));
  }
}
