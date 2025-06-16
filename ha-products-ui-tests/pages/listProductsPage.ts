import { Locator, Page } from "@playwright/test";
import { AddProductDialog } from "./addProductDialog";

export class ListProductsPage {

  private page: Page;

  // Represents the "Add Product" dialog
  public addProductDialog: AddProductDialog;

  private pageTitle: Locator;
  private manufacturerSearchInput: Locator;
  private brandSearchInput: Locator;
  private searchButton: Locator;
  private addButton: Locator;
  private productsTable: Locator;
  private applicationNumberColumn: Locator;
  private manufacturerColumn: Locator;
  private substanceNameColumn: Locator;
  private brandNameColumn: Locator;
  private productNumbersColumn: Locator;

  constructor(page: Page) {
    this.page = page;
    this.addProductDialog = new AddProductDialog(this.page);
    this.pageTitle = this.page.locator("h1.products__title");
    this.manufacturerSearchInput = this.page.locator('[data-e2e="productsSearchInputManufacturer"]');
    this.brandSearchInput = this.page.locator('[data-e2e="brandSearchInput"]');
    this.searchButton = this.page.locator('[data-e2e="productsSearchButton"]');
    this.addButton = this.page.locator('[data-e2e="productsAddButton"]');
    this.productsTable = this.page.locator('[data-e2e="productsTable"]');
    this.applicationNumberColumn = this.page.locator('th.el-table_1_column_1');
    this.manufacturerColumn = this.page.locator('th.el-table_1_column_2');
    this.substanceNameColumn = this.page.locator('th.el-table_1_column_3');
    this.brandNameColumn = this.page.locator('th.el-table_1_column_4');
    this.productNumbersColumn = this.page.locator('th.el-table_1_column_5');
  }

  async navigateTo(url: string) {
    await this.page.goto(url);
  }

  async getPageTitle() {
    return this.pageTitle.textContent();
  }

  async isManufacturerSearchInputVisible() {
    return await this.manufacturerSearchInput.isVisible();
  }

  async fillManufacturerSearchInput(value: string) {
    await this.manufacturerSearchInput.fill(value);
  }

  async isBrandSearchInputVisible() {
    return await this.brandSearchInput.isVisible();
  }

  async fillBrandSearchInput(value: string) {
    await this.brandSearchInput.fill(value);
  }

  async isSearchButtonVisible() {
    return await this.searchButton.isVisible();
  }

  async clickSearchButton() {
    return this.searchButton.click();
  }

  async isAddButtonVisible() {
    return await this.addButton.isVisible();
  }

  async clickAddButton() {
    await this.addButton.click();
    // Wait for the Add Product dialog to become visible
    await this.addProductDialog.isAddProductDialogVisible();
  }

  async isProductsTableVisible() {
    return await this.productsTable.isVisible();
  }

  async getApplicationNumberColumnText() {
    return this.applicationNumberColumn.textContent();
  }

  async getManufacturerColumnText() {
    return this.manufacturerColumn.textContent();
  }

  async getSubstanceNameColumnText() {
    return this.substanceNameColumn.textContent();
  }

  async getBrandNameColumnText() {
    return this.brandNameColumn.textContent();
  }

  async getProductNumbersColumnText() {
    return this.productNumbersColumn.textContent();
  }

  async scrollToBottom() {
    await this.page.evaluate(() => {
      window.scrollTo(0, document.body.scrollHeight);
    });
  }

  async getLastProductRow() {
    const rows = await this.page.$$('tr.el-table__row');
    const lastRow = rows[rows.length - 1];
    const cells = await lastRow.$$('td');
    
    const manufacturer = await cells[1].textContent();
    const substanceName = await cells[2].textContent();
    const brandName = await cells[3].textContent();
    const productNumbers = await cells[4].textContent();

    return {
      manufacturer,
      substanceName,
      brandName,
      productNumbers
    };
  }

  getDisplayedProducts() {
    return this.page.$$eval('tr.el-table__row', rows => {
      return rows.map(row => {
        const cells = row.querySelectorAll('td');
        return {
          applicationNumber: cells[0].textContent?.trim() || '',
          manufacturer: cells[1].textContent?.trim() || '',
          substanceName: cells[2].textContent?.trim() || '',
          brandName: cells[3].textContent?.trim() || '',
          productNumbers: cells[4].textContent?.trim() || ''
        };
      });
    });
  }

}
