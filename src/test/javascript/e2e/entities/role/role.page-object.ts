import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class RoleComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-role div table .btn-danger'));
  title = element.all(by.css('jhi-role div h2#page-heading span')).first();

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

export class RoleUpdatePage {
  pageTitle = element(by.id('jhi-role-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  roleNameInput = element(by.id('field_roleName'));
  roleDescriptionInput = element(by.id('field_roleDescription'));
  roleFlagInput = element(by.id('field_roleFlag'));
  roleEffDateInput = element(by.id('field_roleEffDate'));
  roleExpDateInput = element(by.id('field_roleExpDate'));
  resourcesSelect = element(by.id('field_resources'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setRoleNameInput(roleName) {
    await this.roleNameInput.sendKeys(roleName);
  }

  async getRoleNameInput() {
    return await this.roleNameInput.getAttribute('value');
  }

  async setRoleDescriptionInput(roleDescription) {
    await this.roleDescriptionInput.sendKeys(roleDescription);
  }

  async getRoleDescriptionInput() {
    return await this.roleDescriptionInput.getAttribute('value');
  }

  async setRoleFlagInput(roleFlag) {
    await this.roleFlagInput.sendKeys(roleFlag);
  }

  async getRoleFlagInput() {
    return await this.roleFlagInput.getAttribute('value');
  }

  async setRoleEffDateInput(roleEffDate) {
    await this.roleEffDateInput.sendKeys(roleEffDate);
  }

  async getRoleEffDateInput() {
    return await this.roleEffDateInput.getAttribute('value');
  }

  async setRoleExpDateInput(roleExpDate) {
    await this.roleExpDateInput.sendKeys(roleExpDate);
  }

  async getRoleExpDateInput() {
    return await this.roleExpDateInput.getAttribute('value');
  }

  async resourcesSelectLastOption(timeout?: number) {
    await this.resourcesSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async resourcesSelectOption(option) {
    await this.resourcesSelect.sendKeys(option);
  }

  getResourcesSelect(): ElementFinder {
    return this.resourcesSelect;
  }

  async getResourcesSelectedOption() {
    return await this.resourcesSelect.element(by.css('option:checked')).getText();
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

export class RoleDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-role-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-role'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
