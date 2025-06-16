import { test, expect } from "@playwright/test";
import { ListProductsPage } from "../pages/listProductsPage";

test.describe("Products List Tests", () => {
  
  test("List all products @tc1 @smoke @regression", async ({ page }) => {
    const listProductsPage = new ListProductsPage(page);

    // Given application is running on <baseUrl>
    // When I navigate to <baseUrl>
    await listProductsPage.navigateTo("/");
    await expect(page).toHaveTitle("HA Products");

    // Then "Products List" page is displayed
    const pageTitle = await listProductsPage.getPageTitle();
    expect(pageTitle).toBe("Products List");

    // And "Search by manufacturer" input is present
    const isManufacturerSearchInputVisible = await listProductsPage.isManufacturerSearchInputVisible();
    expect(isManufacturerSearchInputVisible).toBeTruthy();

    // And "Search by brand name" input is present
    const isBrandSearchInputVisible = await listProductsPage.isBrandSearchInputVisible();
    expect(isBrandSearchInputVisible).toBeTruthy();

    // And "Search" button is present
    const isSearchButtonVisible = await listProductsPage.isSearchButtonVisible();
    expect(isSearchButtonVisible).toBeTruthy();

    // And "Add Product" button is present
    const isAddButtonVisible = await listProductsPage.isAddButtonVisible();
    expect(isAddButtonVisible).toBeTruthy();

    // And a table with products is present
    const isProductsTableVisible = await listProductsPage.isProductsTableVisible();
    expect(isProductsTableVisible).toBeTruthy();

    // And the table has the following columns: "Application Number", "Manufacturer", "Substance Name", "Brand Name", "Product Numbers"
    const applicationNumberColumnText = await listProductsPage.getApplicationNumberColumnText();
    expect(applicationNumberColumnText).toBe("Application Number");
    const manufacturerColumnText = await listProductsPage.getManufacturerColumnText();
    expect(manufacturerColumnText).toBe("Manufacturer Name");
    const substanceNameColumnText = await listProductsPage.getSubstanceNameColumnText();
    expect(substanceNameColumnText).toBe("Substance Name");
    const brandNameColumnText = await listProductsPage.getBrandNameColumnText();
    expect(brandNameColumnText).toBe("Brand Name");
    const productNumbersColumnText = await listProductsPage.getProductNumbersColumnText();
    expect(productNumbersColumnText).toBe("Product Numbers");
  });

  test("Create a product and search for it on the list and by using the search functionality @tc11 @e2e", async ({ page }) => {
    const listProductsPage = new ListProductsPage(page);

    // Given I am on "Products List" page
    await listProductsPage.navigateTo("/");
    await expect(page).toHaveTitle("HA Products");

    // When I click on "Add Product" button
    await listProductsPage.clickAddButton();

    // Then "Add Product" dialog is displayed
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

    // When I fill the form with valid data
    const manufacturer = "E2E_Manufacturer_" + Date.now();
    const substanceName = "E2E_Substance_" + Date.now();
    const brandName = "E2E_Brand_" + Date.now();
    const productNumbers = "E2E_ProductNumbers_" + Date.now();
    await listProductsPage.addProductDialog.fillForm(
      manufacturer,
      substanceName,
      brandName,
      productNumbers
    );
    await expect(listProductsPage.addProductDialog.isAddButtonEnabled()).toBeTruthy();
    
    // When I click on "Add" button
    await listProductsPage.addProductDialog.clickAddButton();

    // Then "Products List" page is displayed
    await expect(page).toHaveTitle("HA Products");

    // When we scroll to the bottom of the products table
    await listProductsPage.scrollToBottom();

    // Then the last row has `Manufacturer`, `Substance Name`, `Brand Name`, and `Product Numbers` matching the values we entered
    const lastRow = await listProductsPage.getLastProductRow();
    expect(lastRow.manufacturer).toBe(manufacturer);

    // When I fill in "Search by manufacturer" with <manufacturer>
    await listProductsPage.fillManufacturerSearchInput(manufacturer);
    // And I click on "Search" button
    await listProductsPage.clickSearchButton();
    // Then "Products List" page is displayed
    await expect(page).toHaveTitle("HA Products");
    // Hard wait to ensure the search results are loaded
    await page.waitForTimeout(1000);
    // Then products with "Manufacturer" <manufacturer> are displayed in the table
    const products = await listProductsPage.getDisplayedProducts();
    expect(products).toEqual(
      expect.arrayContaining([
        expect.objectContaining({
          manufacturer: manufacturer
        })
      ])
    );
  });
});
