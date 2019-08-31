import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class UserxComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-userx div table .btn-danger'));
  title = element.all(by.css('jhi-userx div h2#page-heading span')).first();

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

export class UserxUpdatePage {
  pageTitle = element(by.id('jhi-userx-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  loginInput = element(by.id('field_login'));
  passwordInput = element(by.id('field_password'));
  firstNameInput = element(by.id('field_firstName'));
  lastNameInput = element(by.id('field_lastName'));
  emailInput = element(by.id('field_email'));
  activatedInput = element(by.id('field_activated'));
  langKeyInput = element(by.id('field_langKey'));
  imageUrlInput = element(by.id('field_imageUrl'));
  activationKeyInput = element(by.id('field_activationKey'));
  resetKeyInput = element(by.id('field_resetKey'));
  resetDateInput = element(by.id('field_resetDate'));
  organizationSelect = element(by.id('field_organization'));
  rolesSelect = element(by.id('field_roles'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setLoginInput(login) {
    await this.loginInput.sendKeys(login);
  }

  async getLoginInput() {
    return await this.loginInput.getAttribute('value');
  }

  async setPasswordInput(password) {
    await this.passwordInput.sendKeys(password);
  }

  async getPasswordInput() {
    return await this.passwordInput.getAttribute('value');
  }

  async setFirstNameInput(firstName) {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput() {
    return await this.firstNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName) {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput() {
    return await this.lastNameInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
  }

  getActivatedInput(timeout?: number) {
    return this.activatedInput;
  }
  async setLangKeyInput(langKey) {
    await this.langKeyInput.sendKeys(langKey);
  }

  async getLangKeyInput() {
    return await this.langKeyInput.getAttribute('value');
  }

  async setImageUrlInput(imageUrl) {
    await this.imageUrlInput.sendKeys(imageUrl);
  }

  async getImageUrlInput() {
    return await this.imageUrlInput.getAttribute('value');
  }

  async setActivationKeyInput(activationKey) {
    await this.activationKeyInput.sendKeys(activationKey);
  }

  async getActivationKeyInput() {
    return await this.activationKeyInput.getAttribute('value');
  }

  async setResetKeyInput(resetKey) {
    await this.resetKeyInput.sendKeys(resetKey);
  }

  async getResetKeyInput() {
    return await this.resetKeyInput.getAttribute('value');
  }

  async setResetDateInput(resetDate) {
    await this.resetDateInput.sendKeys(resetDate);
  }

  async getResetDateInput() {
    return await this.resetDateInput.getAttribute('value');
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

  async rolesSelectLastOption(timeout?: number) {
    await this.rolesSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async rolesSelectOption(option) {
    await this.rolesSelect.sendKeys(option);
  }

  getRolesSelect(): ElementFinder {
    return this.rolesSelect;
  }

  async getRolesSelectedOption() {
    return await this.rolesSelect.element(by.css('option:checked')).getText();
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

export class UserxDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-userx-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-userx'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
