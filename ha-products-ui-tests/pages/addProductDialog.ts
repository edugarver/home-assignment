import { Locator, Page } from '@playwright/test';

export class AddProductDialog {

  private addProductDialog: Locator;
  private addProductDialogCloseButton: Locator;
  private addProductDialogCancelButton: Locator;
  private addProductDialogAddButton: Locator;
  private manufacturerInput: Locator;
  private substanceNameInput: Locator;
  private brandNameInput: Locator;
  private productNumbersInput: Locator;
  private errorMessage: Locator;

  constructor(private page: Page) {
    this.addProductDialog = this.page.locator('div.el-dialog');
    this.addProductDialogCloseButton = this.page.locator('button.el-dialog__headerbtn');
    this.addProductDialogCancelButton = this.page.locator('[data-e2e="productsAddCancelButton"]');
    this.addProductDialogAddButton = this.page.locator('[data-e2e="productsAddSaveButton"]');
    this.manufacturerInput = this.page.locator('[data-e2e="productsAddManufacturerInput"]');
    this.substanceNameInput = this.page.locator('[data-e2e="productsAddSubstanceNameInput"]');
    this.brandNameInput = this.page.locator('[data-e2e="productsAddBrandNameInput"]');
    this.productNumbersInput = this.page.locator('[data-e2e="productsAddProductNumbersInput"]');
    this.errorMessage = this.page.locator('div.el-form-item__error');
  }

  async isAddProductDialogVisible() {
    return await this.addProductDialog.isVisible();
  }

  async clickCloseButton() {
    await this.addProductDialogCloseButton.click();
  }

  async isCancelButtonVisible() {
    return await this.addProductDialogCancelButton.isVisible();
  }

  async clickCancelButton() {
    await this.addProductDialogCancelButton.click({ timeout: 5000 });
  }

  async isAddButtonVisible() {
    return await this.addProductDialogAddButton.isVisible();
  }

  async isAddButtonEnabled() {
    // Wait for add button to be enabled
    await this.addProductDialogAddButton.waitFor({ state: 'attached' });
    return await this.addProductDialogAddButton.isEnabled();
  }

  async isAddButtonDisabled() {
    return await this.addProductDialogAddButton.isDisabled();
  }

  async clickAddButton() {
    await this.addProductDialogAddButton.click({ timeout: 5000 });
    // Wait for the dialog to close after clicking Add
    await this.addProductDialog.waitFor({ state: 'detached' });
  }

  async isManufacturerInputVisible() {
    return await this.manufacturerInput.isVisible();
  }
  
  async fillManufacturerInput(value: string) {
    await this.manufacturerInput.fill(value);
  }
  
  async isSubstanceNameInputVisible() {
    return await this.substanceNameInput.isVisible();
  }

  async fillSubstanceNameInput(value: string) {
    await this.substanceNameInput.fill(value);
  }

  async isBrandNameInputVisible() {
    return await this.brandNameInput.isVisible();
  }

  async fillBrandNameInput(value: string) {
    await this.brandNameInput.fill(value);
  }

  async isProductNumbersInputVisible() {
    return await this.productNumbersInput.isVisible();
  }

  async fillProductNumbersInput(value: string) {
    await this.productNumbersInput.fill(value);
  }

  async fillForm(manufacturer: string, substanceName: string, brandName: string, productNumbers: string) {
    await this.fillManufacturerInput(manufacturer);
    await this.fillSubstanceNameInput(substanceName);
    await this.fillBrandNameInput(brandName);
    await this.fillProductNumbersInput(productNumbers);
  }

  async getErrorMessage() {
    return await this.errorMessage.textContent();
  }

  async isErrorMessageVisible() {
    return await this.errorMessage.isVisible();
  }

}