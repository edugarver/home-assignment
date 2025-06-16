# ha-products-be-tests

Automated API testing suite for the HA Products service endpoints using Java and RestAssured.
Tests are divided into four separate files, each representing a different functionality of the application:
- CreateProductsTest: Tests the creation of products.
- E2EProductsTest: End-to-end tests that cover the entire flow of creating and retrieving products.
- ListProductsTest: Tests the retrieval of products.
- SearchProductsTest: Tests the search functionality for products.
In addition, each test is tagged with different labels to categorize them into smoke, regression, and e2e tests, and an individual tag with their id for each test case.
Some tests are disabled due to existing issues or limitations in the API, such as type validation or specific field length constraints.
The automated test cases are the following ones:
```plain
- List all products @tc1 @smoke @regression
- Create a product @tc2 @smoke @regression
- Search for a product @tc3 @smoke @regression
- Create a product with only required properties @tc4 @regression
- Create a product with an empty manufacturer @tc5 @regression @disabled
- Create a product with an empty substance name @tc6 @regression
- Type validation for create product request @tc7 @regression @disabled
- Create a product with a manufacturer longer than 50 characters @tc8 @regression
- Create a product with a substance name longer than 50 characters @tc9 @regression @disabled
- Create a product with a brand name longer than 50 characters @tc10 @regression
- Create a product with product numbers longer than 1000 characters @tc11 @regression
- Search for a product with empty request body @tc12 @regression
- Search for a product with only manufacturer @tc13 @regression
- Search for a product with only brand name @tc14 @regression
- Search for a product with non-existing manufacturer @tc15 @regression
- Search for a product with non-existing brand name @tc16 @regression
- Type validation for search product request @tc17 @regression @disabled
```
## Project Structure
- `src/main/java/com/ha/products/config/ApiConfig`: Class that builds the request specifications used later during the testing phase.
- `src/main/java/com/ha/products/models`: Contains models for the requests and responses that will be used in the tests, making use of Lombok to reduce boilerplate code.
- `src/test/java/com/ha/products/tests`: Contains the test classes that run with JUnit5 and RestAssured. Each class contains tests for specific endpoints or functionalities.
- `src/test/java/com/ha/products/utils`: Package containing utility class
- `src/test/java/com/ha/products/BaseApiTest`: Base class for API tests, providing common setup and teardown methods.
- `src/test/resources/config.properties`: Configuration file for the API base URL.
- `src/test/resources/schemas`: JSON files representing the expected request and response schemas

## Prerequisites
- Java 23 or higher
- Maven
- A running instance of the ha-products-be project (the baseUrl is set in the `config.properties` file)

## Technologies & Dependencies
- Java 23 
- Maven
- JUnit 5 - Test framework
- RestAssured - API testing library
- Allure - Test reporting
- Project Lombok - Reduces boilerplate code
- Jackson - JSON parsing/serialization

## Installation
Once you have cloned the parent repository, navigate to the `ha-products-be-tests` directory and run the following command to install the dependencies:
```bash
mvn clean install
```
## Configuration
Update the API base URL in src/test/resources/config.properties

## Running Tests
To run all tests:
```bash
mvn clean test
```
To run different suites based on the groups defined for each test:
```bash
mvn clean test -Dgroups="e2e"
```
The groups can be `smoke`, `regression`, or `e2e`.
You can also run a specific test by providing the test tag, e.g. tc1:
```bash
mvn clean test -Dgroups="tc1"
```

## Reporting
To generate Allure report:
```bash
mvn allure:report
```
Then open target/site/allure-maven-plugin/index.html in your browser.

## Notes
- The tests are designed to be run against a live instance of the HA Products service.
- Some tests are currently disabled due to known issues or limitations in the API. These can be enabled once the issues are resolved.
- Ensure that the API is running and accessible at the base URL specified in the configuration file before running the tests.
- The tests assume that some data is already present in the database, such as products with specific manufacturers and brands. You may need to adjust the tests or the data in the database accordingly.
