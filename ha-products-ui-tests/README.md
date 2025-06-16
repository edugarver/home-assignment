# ha-products-ui-tests

## Description
This folder contains UI tests for the Home Assignment UI project using Playwright.
Tests are divided into three separate files, each representing a different functionality of the application:
- `createProduct.test.js` - Tests for creating a product.
- `listProducts.test.js` - Tests for listing products.
- `searchProducts.test.js` - Tests for searching products.
In addition, each test is tagged with different labels to categorize them into smoke, regression, and e2e tests, and an individual tag with their id for each test case.
One test is skipped due to time constraint limitations when it comes to finding a correct locator for the error messages (should use XPath locators).
The automated test cases are the following ones
```plain
- List all products @tc1 @smoke @regression
- Create a product @tc2 @smoke @regression
- Search for a product @tc3 @smoke @regression
- Add button is disabled when required fields are empty @tc4 @regression
- Cancel button closes the dialog without adding a product @tc5 @regression
- Search for a product with empty fields @tc6 @regression
- Search with only manufacturer input @tc7 @regression
- Search with only brand name input @tc8 @regression
- Search with non-existing manufacturer @tc9 @regression
- Search with non-existing brand name @tc10 @regression
- Create a product and search for it on the list and by using the search functionality @tc11 @e2e
- Max length for fields @tc12 @regression @skipped
```

## Project structure
- `tests/` - Contains the test files. The tests are organized into different files based on their functionality.
- `pages/` - Contains the page objects that represent the UI components.
- `playwright.config.js` - Configuration file for Playwright.
- `package.json` - Contains the project dependencies and scripts.
- `.env` - Configuration file where you can set the base URL for the Home Assignment UI project.
- `allure-results/` - Directory where Allure test results are stored.

## Prerequisites
To run the tests, you need to have the following installed:
- Node.js (version 14 or higher)
- npm (Node Package Manager)
- Playwright (installed as a dependency in this project)
- Allure Commandline (for generating reports)
- A running instance of the ha-products-ui project (the base URL should be set in the `.env` file)
- A browser installed (the tests are set to run on Chrome by default, but you can configure it to run on other browsers as well)
- Git (to clone the repository)
- A code editor or IDE (like Visual Studio Code) to open and edit the project files

## Installation
Once you have cloned the parent repository, open this folder in your IDE and run the following command to install the necessary dependencies:
```bash
npm install
```

## Configuration
You must specify where the Home Assignment UI project is running. Modify the .env file and set the `BASE_URL` variable to the desired URL

## Running Tests
```bash
# Run all tests
npx playwright test

# Run tests with a specific tag
npx playwright test --grep @tag

# Generate and view Allure reports
npx playwright test
allure generate allure-results --clean
allure open

```

## Scripts
- `npm test` - Runs all tests.
- `npm run test:smoke` - Runs smoke tests.
- `npm run test:regression` - Runs regression tests.
- `npm run test:e2e` - Runs e2e tests.

## Reporting
By default, Playwright generates its HTML report that opens automatically if there is a failure in the tests. You can view it by running:
```bash
npx playwright show-report
```
You can also generate Allure reports for better visualization and analysis of test results.\
How to use Allure reports:
1. Install Allure CLI
```bash
npm install -g allure-commandline
```
2. Run the tests with Playwright to generate the Allure results
```bash
npx playwright test
```
3. Generate the Allure report
```bash
allure generate allure-results --clean
```

## Notes
- I personally prefer to encapsulate all the interactions with the elements in a dedicated method, and then use that method in the tests, although this makes the classes very large.
- For simplicity purposes, the tests only run on chrome, but it's possible to run them on other browsers as well
- Allure annotations are not used in the tests, but they can be added to provide more context and information about the test steps
- Playwright run in parallel by default, but it is disabled for data consistency purposes
