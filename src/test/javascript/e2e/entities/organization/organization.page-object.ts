import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class OrganizationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-organization div table .btn-danger'));
  title = element.all(by.css('jhi-organization div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class OrganizationUpdatePage {
  pageTitle = element(by.id('jhi-organization-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  orgNameInput = element(by.id('field_orgName'));
  orgCodeInput = element(by.id('field_orgCode'));
  orgFlagInput = element(by.id('field_orgFlag'));
  orgAreaCodeInput = element(by.id('field_orgAreaCode'));
  orgAreaNameInput = element(by.id('field_orgAreaName'));
  orgDescriptionInput = element(by.id('field_orgDescription'));
  orgLftInput = element(by.id('field_orgLft'));
  orgRgtInput = element(by.id('field_orgRgt'));
  orgLevelInput = element(by.id('field_orgLevel'));
  orgOrderInput = element(by.id('field_orgOrder'));
  leafInput = element(by.id('field_leaf'));
  upperSelect = element(by.id('field_upper'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setOrgNameInput(orgName) {
    await this.orgNameInput.sendKeys(orgName);
  }

  async getOrgNameInput() {
    return await this.orgNameInput.getAttribute('value');
  }

  async setOrgCodeInput(orgCode) {
    await this.orgCodeInput.sendKeys(orgCode);
  }

  async getOrgCodeInput() {
    return await this.orgCodeInput.getAttribute('value');
  }

  async setOrgFlagInput(orgFlag) {
    await this.orgFlagInput.sendKeys(orgFlag);
  }

  async getOrgFlagInput() {
    return await this.orgFlagInput.getAttribute('value');
  }

  async setOrgAreaCodeInput(orgAreaCode) {
    await this.orgAreaCodeInput.sendKeys(orgAreaCode);
  }

  async getOrgAreaCodeInput() {
    return await this.orgAreaCodeInput.getAttribute('value');
  }

  async setOrgAreaNameInput(orgAreaName) {
    await this.orgAreaNameInput.sendKeys(orgAreaName);
  }

  async getOrgAreaNameInput() {
    return await this.orgAreaNameInput.getAttribute('value');
  }

  async setOrgDescriptionInput(orgDescription) {
    await this.orgDescriptionInput.sendKeys(orgDescription);
  }

  async getOrgDescriptionInput() {
    return await this.orgDescriptionInput.getAttribute('value');
  }

  async setOrgLftInput(orgLft) {
    await this.orgLftInput.sendKeys(orgLft);
  }

  async getOrgLftInput() {
    return await this.orgLftInput.getAttribute('value');
  }

  async setOrgRgtInput(orgRgt) {
    await this.orgRgtInput.sendKeys(orgRgt);
  }

  async getOrgRgtInput() {
    return await this.orgRgtInput.getAttribute('value');
  }

  async setOrgLevelInput(orgLevel) {
    await this.orgLevelInput.sendKeys(orgLevel);
  }

  async getOrgLevelInput() {
    return await this.orgLevelInput.getAttribute('value');
  }

  async setOrgOrderInput(orgOrder) {
    await this.orgOrderInput.sendKeys(orgOrder);
  }

  async getOrgOrderInput() {
    return await this.orgOrderInput.getAttribute('value');
  }

  getLeafInput(timeout?: number) {
    return this.leafInput;
  }

  async upperSelectLastOption(timeout?: number) {
    await this.upperSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async upperSelectOption(option) {
    await this.upperSelect.sendKeys(option);
  }

  getUpperSelect(): ElementFinder {
    return this.upperSelect;
  }

  async getUpperSelectedOption() {
    return await this.upperSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class OrganizationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-organization-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-organization'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
