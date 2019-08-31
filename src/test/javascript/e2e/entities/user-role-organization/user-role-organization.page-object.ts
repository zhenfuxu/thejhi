import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class UserRoleOrganizationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-user-role-organization div table .btn-danger'));
  title = element.all(by.css('jhi-user-role-organization div h2#page-heading span')).first();

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

export class UserRoleOrganizationUpdatePage {
  pageTitle = element(by.id('jhi-user-role-organization-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  userNameInput = element(by.id('field_userName'));
  roleNameInput = element(by.id('field_roleName'));
  orgNameInput = element(by.id('field_orgName'));
  userSelect = element(by.id('field_user'));
  roleSelect = element(by.id('field_role'));
  organizationSelect = element(by.id('field_organization'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUserNameInput(userName) {
    await this.userNameInput.sendKeys(userName);
  }

  async getUserNameInput() {
    return await this.userNameInput.getAttribute('value');
  }

  async setRoleNameInput(roleName) {
    await this.roleNameInput.sendKeys(roleName);
  }

  async getRoleNameInput() {
    return await this.roleNameInput.getAttribute('value');
  }

  async setOrgNameInput(orgName) {
    await this.orgNameInput.sendKeys(orgName);
  }

  async getOrgNameInput() {
    return await this.orgNameInput.getAttribute('value');
  }

  async userSelectLastOption(timeout?: number) {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async roleSelectLastOption(timeout?: number) {
    await this.roleSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async roleSelectOption(option) {
    await this.roleSelect.sendKeys(option);
  }

  getRoleSelect(): ElementFinder {
    return this.roleSelect;
  }

  async getRoleSelectedOption() {
    return await this.roleSelect.element(by.css('option:checked')).getText();
  }

  async organizationSelectLastOption(timeout?: number) {
    await this.organizationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async organizationSelectOption(option) {
    await this.organizationSelect.sendKeys(option);
  }

  getOrganizationSelect(): ElementFinder {
    return this.organizationSelect;
  }

  async getOrganizationSelectedOption() {
    return await this.organizationSelect.element(by.css('option:checked')).getText();
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

export class UserRoleOrganizationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userRoleOrganization-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userRoleOrganization'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
