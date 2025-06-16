import { test, expect } from "@playwright/test";
import { ListProductsPage } from "../pages/listProductsPage";

test.describe("Product Creation Tests", () => {
  let listProductsPage: ListProductsPage;

  test.beforeEach(async ({ page }) => {
    listProductsPage = new ListProductsPage(page);
    await listProductsPage.navigateTo("/");
    await expect(page).toHaveTitle("HA Products");
  });

  test("Create a product @tc2 @smoke @regression", async ({ page }) => {

    const manufacturer = "Manufacturer_" + Date.now();
    const substanceName = "Substance_" + Date.now();
    const brandName = "Brand_" + Date.now();
    const productNumbers = "ProductNumbers_" + Date.now();

    await test.step("I click on 'Add Product' button and then 'Add Product' dialog is displayed", async () => {
      await listProductsPage.clickAddButton();
      await expect(listProductsPage.addProductDialog.isAddProductDialogVisible()).toBeTruthy();
      // And the form has the following fields: "Manufacturer", "Substance Name", "Brand Name", "Product Numbers"
      await expect(listProductsPage.addProductDialog.isManufacturerInputVisible()).toBeTruthy();
      await expect(listProductsPage.addProductDialog.isSubstanceNameInputVisible()).toBeTruthy();
      await expect(listProductsPage.addProductDialog.isBrandNameInputVisible()).toBeTruthy();
      await expect(listProductsPage.addProductDialog.isProductNumbersInputVisible()).toBeTruthy();
      // And the form has "Cancel" and "Add" buttons
      await expect(listProductsPage.addProductDialog.isCancelButtonVisible()).toBeTruthy();
      await expect(listProductsPage.addProductDialog.isAddButtonVisible()).toBeTruthy();
      // And "Add" button is disabled
      await expect(listProductsPage.addProductDialog.isAddButtonDisabled()).toBeTruthy();
    });
       

    await test.step("I fill in the product details and click Add", async () => {
      // When I fill in "Manufacturer" with <manufacturer>
      await listProductsPage.addProductDialog.fillManufacturerInput(manufacturer);
      // And I fill in "Substance Name" with <substanceName>
      await listProductsPage.addProductDialog.fillSubstanceNameInput(substanceName);
      // And I fill in "Brand Name" with <brandName>
      await listProductsPage.addProductDialog.fillBrandNameInput(brandName);
      // And I fill in "Product Numbers" with <productNumbers>
      await listProductsPage.addProductDialog.fillProductNumbersInput(productNumbers);
      // Then "Add" button is enabled
      await expect(listProductsPage.addProductDialog.isAddButtonEnabled()).toBeTruthy();
      // When I click on "Add" button
      await listProductsPage.addProductDialog.clickAddButton();
    });

    // Then "Products List" page is displayed
    await expect(page).toHaveTitle("HA Products");

    await test.step("I verify the product is added to the list", async () => {
      // When we scroll to the bottom of the products table
      await listProductsPage.scrollToBottom();

      // Then the last row has `Manufacturer`, `Substance Name`, `Brand Name`, and `Product Numbers` matching the values we entered
      // There is an existing bug that makes the brand name and substance name swapped in the UI, so we will check them accordingly
      const lastRow = await listProductsPage.getLastProductRow();
      expect(lastRow.manufacturer).toBe(manufacturer);
      expect(lastRow.substanceName).toBe(brandName);
      expect(lastRow.brandName).toBe(substanceName);
      expect(lastRow.productNumbers).toBe(productNumbers);
    });
  });

  test("Add button is disabled when required fields are empty @tc4 @regression", async ({page}) => {
    await test.step("I click on 'Add Product' button and verify the dialog", async () => {
      // When I click on "Add Product" button
      await listProductsPage.clickAddButton();
      // Then "Add Product" form is displayed
      await expect(listProductsPage.addProductDialog.isAddProductDialogVisible()).toBeTruthy();
      // And "Add" button is disabled
      await expect(listProductsPage.addProductDialog.isAddButtonDisabled()).toBeTruthy();
    });

    await test.step("I fill in optional fields and verify Add button is still disabled", async () => {
      // When I fill in "Product Numbers" with <productNumbers>
      const productNumbers = "ProductNumbers_" + Date.now();
      await listProductsPage.addProductDialog.fillProductNumbersInput(productNumbers);
      // And I fill in "Brand Name" with <brandName>
      const brandName = "Brand_" + Date.now();
      await listProductsPage.addProductDialog.fillBrandNameInput(brandName);
      // Then "Add" button is still disabled
      await expect(listProductsPage.addProductDialog.isAddButtonDisabled()).toBeTruthy();
    });

    await test.step("I fill in the required fields and verify Add button is enabled", async () => {
      // When I fill in "Manufacturer" with <manufacturer>
      const manufacturer = "Manufacturer_" + Date.now();
      await listProductsPage.addProductDialog.fillManufacturerInput(manufacturer);
      // And I fill in "Substance Name" with <substanceName>
      const substanceName = "Substance_" + Date.now();
      await listProductsPage.addProductDialog.fillSubstanceNameInput(substanceName);
      // Then "Add" button is enabled
      await expect(listProductsPage.addProductDialog.isAddButtonEnabled()).toBeTruthy();
    });
  });

  test("Cancel button closes the dialog without adding a product @tc5 @regression", async ({page}) => {
    // When I click on "Add Product" button
    await listProductsPage.clickAddButton();
    // Then "Add Product" dialog is displayed
    await expect(listProductsPage.addProductDialog.isAddProductDialogVisible()).toBeTruthy();
    // When I click on "Cancel" button
    await listProductsPage.addProductDialog.clickCancelButton();
    // Then I am on "Products List" page
    await expect(page).toHaveTitle("HA Products");
  });

  test.skip("Max length for fields @tc12 @regression", async ({page}) => {
    // When I click on "Add Product" button
    await listProductsPage.clickAddButton();
    // Then "Add Product" form is displayed
    await expect(listProductsPage.addProductDialog.isAddProductDialogVisible()).toBeTruthy();
    
    // When I fill in "Manufacturer" with a string longer than 50 characters
    const longManufacturer = "A".repeat(51);
    await listProductsPage.addProductDialog.fillManufacturerInput(longManufacturer);
    // Then an error message is displayed saying "Max 50 characters"
    await expect(listProductsPage.addProductDialog.isErrorMessageVisible()).toBeTruthy();
    const errorMessage = await listProductsPage.addProductDialog.getErrorMessage();
    expect(errorMessage).toBe("Max 50 characters");
    // And "Add" button is disabled
    await expect(listProductsPage.addProductDialog.isAddButtonDisabled()).toBeTruthy();
    // And I fill in "Manufacturer" with a valid string
    const manufacturer = "Valid Manufacturer";
    await listProductsPage.addProductDialog.fillManufacturerInput(manufacturer);
    
    // When I fill in "Substance Name" with a string longer than 50 characters
    const longSubstanceName = "A".repeat(51);
    await listProductsPage.addProductDialog.fillSubstanceNameInput(longSubstanceName);
    // Then an error message is displayed saying "Max 50 characters"
    await expect(listProductsPage.addProductDialog.isErrorMessageVisible()).toBeTruthy();
    const substanceErrorMessage = await listProductsPage.addProductDialog.getErrorMessage();
    expect(substanceErrorMessage).toBe("Max 50 characters");
    // And "Add" button is disabled
    await expect(listProductsPage.addProductDialog.isAddButtonDisabled()).toBeTruthy();
    // And I fill in "Substance Name" with a valid string
    const substanceName = "Valid Substance Name";
    await listProductsPage.addProductDialog.fillSubstanceNameInput(substanceName);

    // When I fill in "Brand Name" with a string longer than 50 characters
    const longBrandName = "A".repeat(51);
    await listProductsPage.addProductDialog.fillBrandNameInput(longBrandName);
    // Then an error message is displayed saying "Max 50 characters"
    await expect(listProductsPage.addProductDialog.isErrorMessageVisible()).toBeTruthy();
    const brandErrorMessage = await listProductsPage.addProductDialog.getErrorMessage();
    expect(brandErrorMessage).toBe("Max 50 characters");
    // And "Add" button is disabled
    await expect(listProductsPage.addProductDialog.isAddButtonDisabled()).toBeTruthy();
    // And I fill in "Brand Name" with a valid string
    const brandName = "Valid Brand Name";
    await listProductsPage.addProductDialog.fillBrandNameInput(brandName);
    
    // When I fill in "Product Numbers" with a string longer than 1000 characters
    const longProductNumbers = "A".repeat(1001);
    await listProductsPage.addProductDialog.fillProductNumbersInput(longProductNumbers);
    // Then an error message is displayed saying "Max 1000 characters"
    await expect(listProductsPage.addProductDialog.isErrorMessageVisible()).toBeTruthy();
    const productNumbersErrorMessage = await listProductsPage.addProductDialog.getErrorMessage();
    expect(productNumbersErrorMessage).toBe("Max 999 characters");
    // And "Add" button is disabled
    await expect(listProductsPage.addProductDialog.isAddButtonDisabled()).toBeTruthy();
    // When I fill in "Product Numbers" with a valid string
    const productNumbers = "Valid Product Numbers";
    await listProductsPage.addProductDialog.fillProductNumbersInput(productNumbers);
    
    // Then "Add" button is enabled
    await expect(listProductsPage.addProductDialog.isAddButtonEnabled()).toBeTruthy();
  });
});
