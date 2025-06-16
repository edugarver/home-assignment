package com.ha.products.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Getter;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiConfig {
  private static Properties props;
  @Getter
  private static String baseUrl;

  @BeforeAll
  public static void setup() {
    loadProperties();
    baseUrl = props.getProperty("api.base.url");

    RestAssured.baseURI = baseUrl;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  private static void loadProperties() {
    props = new Properties();
    try {
      props.load(new FileInputStream("src/test/resources/config.properties"));
    } catch (IOException e) {
      throw new RuntimeException("Cannot load configuration file", e);
    }
  }

  public static RequestSpecification getRequestSpec() {
    return new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .addFilter(new RequestLoggingFilter())
        .addFilter(new ResponseLoggingFilter())
        .build();
  }

  public static ResponseSpecification getResponseSpec() {
    return new ResponseSpecBuilder()
        .expectContentType(ContentType.JSON)
        .build();
  }

}