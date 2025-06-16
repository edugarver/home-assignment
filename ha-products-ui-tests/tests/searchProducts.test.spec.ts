import { expect, test } from "@playwright/test";
import { ListProductsPage } from "../pages/listProductsPage";

test.describe("Product Search Tests", () => {
  test("Search for a product @tc3 @smoke @regression", async ({ page }) => {
    const listProductsPage = new ListProductsPage(page);
    const manufacturer = "Manufacturer_" + Date.now();
    const substanceName = "Substance_" + Date.now();
    const brandName = "Brand_" + Date.now();
    const productNumbers = "ProductNumbers_" + Date.now();

      // Create a product to search for
      await listProductsPage.navigateTo("/");
      await listProductsPage.clickAddButton();
      await listProductsPage.addProductDialog.fillForm(
        manufacturer,
        substanceName,
        brandName,
        productNumbers
      );
      await listProductsPage.addProductDialog.clickAddButton();

    // Given I am on "Products List" page
    await expect(page).toHaveTitle("HA Products");

      // And I fill in "Search by manufacturer" with <manufacturer>
      await listProductsPage.fillManufacturerSearchInput(manufacturer);
      // And I fill in "Search by brand name" with <brandName>
      await listProductsPage.fillBrandSearchInput(substanceName);
      // When I click on "Search" button
      await listProductsPage.clickSearchButton();

    // Then "Products List" page is displayed
    await expect(page).toHaveTitle("HA Products");

    // Expect that all displayed products match the search criteria
    const products = await listProductsPage.getDisplayedProducts();
    expect(products).toEqual(
      expect.arrayContaining([
        expect.objectContaining({
          manufacturer: manufacturer,
          brandName: substanceName
        })
      ])
    );
  });

  test("Search for a product with empty fields @tc6 @regression", async ({ page }) => {
    const listProductsPage = new ListProductsPage(page);

    // Given I am on "Products List" page
    await listProductsPage.navigateTo("/");
    await expect(page).toHaveTitle("HA Products");

    // When I click on "Search" button without filling any fields
    await listProductsPage.clickSearchButton();
    // Then "Products List" page is displayed
    await expect(page).toHaveTitle("HA Products");
    // Expect that all products are displayed
    const products = await listProductsPage.getDisplayedProducts();
    expect(products.length).toBeGreaterThan(0);
  });

  test("Search with only manufacturer input @tc7 @regression", async ({ page }) => {
    const listProductsPage = new ListProductsPage(page);
    const manufacturer = "Manufacturer_" + Date.now();
    const substanceName = "Substance_" + Date.now();
    const brandName = "Brand_" + Date.now();
    const productNumbers = "ProductNumbers_" + Date.now();

    test.step("Create a product to search for", async () => {
      // Create a product to search for
      await listProductsPage.navigateTo("/");
      await listProductsPage.clickAddButton();
      await listProductsPage.addProductDialog.fillForm(
        manufacturer,
        substanceName,
        brandName,
        productNumbers
      );
      await listProductsPage.addProductDialog.clickAddButton();
    });

    // Given I am on "Products List" page
    await expect(page).toHaveTitle("HA Products");

    // When I fill in "Search by manufacturer" with <manufacturer>
    await listProductsPage.fillManufacturerSearchInput(manufacturer);
    // And I click on "Search" button
    await listProductsPage.clickSearchButton();
    // Then "Products List" page is displayed
    await expect(page).toHaveTitle("HA Products");

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

  test("Search with only brand name input @tc8 @regression", async ({ page }) => {
    const listProductsPage = new ListProductsPage(page);
    const manufacturer = "Manufacturer_" + Date.now();
    const substanceName = "Substance_" + Date.now();
    const brandName = "Brand_" + Date.now();
    const productNumbers = "ProductNumbers_" + Date.now();

      // Create a product to search for
      await listProductsPage.navigateTo("/");
      await listProductsPage.clickAddButton();
      await listProductsPage.addProductDialog.fillForm(
        manufacturer,
        substanceName,
        brandName,
        productNumbers
      );
      await listProductsPage.addProductDialog.clickAddButton();

    // Given I am on "Products List" page
    await expect(page).toHaveTitle("HA Products");

    // When I fill in "Search by brand name" with <brandName>
    await listProductsPage.fillBrandSearchInput(substanceName);
    // And I click on "Search" button
    await listProductsPage.clickSearchButton();
    // Then "Products List" page is displayed
    await expect(page).toHaveTitle("HA Products");

    // Then products with "Brand Name" <brandName> are displayed in the table
    const products = await listProductsPage.getDisplayedProducts();
    expect(products).toEqual(
      expect.arrayContaining([
        expect.objectContaining({
          brandName: substanceName
        })
      ])
    );
  });

  test("Search with non-existing manufacturer @tc9 @regression", async ({
    page
  }) => {
    const listProductsPage = new ListProductsPage(page);

    // Given I am on "Products List" page
    await listProductsPage.navigateTo("/");
    await expect(page).toHaveTitle("HA Products");

    // When I fill in "Search by manufacturer" with <nonExistingManufacturer>
    const nonExistingManufacturer = "NonExisting_" + Date.now();
    await listProductsPage.fillManufacturerSearchInput(nonExistingManufacturer);

    // And I click on "Search" button
    await listProductsPage.clickSearchButton();

    // Then no products are displayed in the table
    const products = await listProductsPage.getDisplayedProducts();
    expect(products.length).toBe(0);
  });

  test("Search with non-existing brand name @tc10 @regression", async ({
    page
  }) => {
    const listProductsPage = new ListProductsPage(page);

    // Given I am on "Products List" page
    await listProductsPage.navigateTo("/");
    await expect(page).toHaveTitle("HA Products");

    // When I fill in "Search by brand name" with <nonExistingBrandName>
    const nonExistingBrandName = "NonExisting_" + Date.now();
    await listProductsPage.fillBrandSearchInput(nonExistingBrandName);

    // And I click on "Search" button
    await listProductsPage.clickSearchButton();

    // Then no products are displayed in the table
    const products = await listProductsPage.getDisplayedProducts();
    expect(products.length).toBe(0);
  });
});
