# ha-products-be-tests

API test automation project for testing product service endpoints.
Some tests are disabled

## Technologies

- Java 11
- Maven
- JUnit 5
- RestAssured
- Allure Reporting

## Project Structure

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

To generate Allure report:

```bash
mvn allure:report
```
Then open target/site/allure-maven-plugin/index.html in your browser.