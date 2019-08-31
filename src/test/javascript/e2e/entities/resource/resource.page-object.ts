import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ResourceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-resource div table .btn-danger'));
  title = element.all(by.css('jhi-resource div h2#page-heading span')).first();

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

export class ResourceUpdatePage {
  pageTitle = element(by.id('jhi-resource-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  resRouterLinkInput = element(by.id('field_resRouterLink'));
  resDescriptionInput = element(by.id('field_resDescription'));
  resFlagInput = element(by.id('field_resFlag'));
  resOperateInput = element(by.id('field_resOperate'));
  resHrefInput = element(by.id('field_resHref'));
  resSrcInput = element(by.id('field_resSrc'));
  resTextInput = element(by.id('field_resText'));
  resClassInput = element(by.id('field_resClass'));
  resEffDateInput = element(by.id('field_resEffDate'));
  resExpDateInput = element(by.id('field_resExpDate'));
  resLftInput = element(by.id('field_resLft'));
  resRgtInput = element(by.id('field_resRgt'));
  resLevelInput = element(by.id('field_resLevel'));
  resOrderInput = element(by.id('field_resOrder'));
  leafInput = element(by.id('field_leaf'));
  resDisabledInput = element(by.id('field_resDisabled'));
  resCheckedInput = element(by.id('field_resChecked'));
  resExpandedInput = element(by.id('field_resExpanded'));
  resSelectedInput = element(by.id('field_resSelected'));
  upperSelect = element(by.id('field_upper'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setResRouterLinkInput(resRouterLink) {
    await this.resRouterLinkInput.sendKeys(resRouterLink);
  }

  async getResRouterLinkInput() {
    return await this.resRouterLinkInput.getAttribute('value');
  }

  async setResDescriptionInput(resDescription) {
    await this.resDescriptionInput.sendKeys(resDescription);
  }

  async getResDescriptionInput() {
    return await this.resDescriptionInput.getAttribute('value');
  }

  async setResFlagInput(resFlag) {
    await this.resFlagInput.sendKeys(resFlag);
  }

  async getResFlagInput() {
    return await this.resFlagInput.getAttribute('value');
  }

  async setResOperateInput(resOperate) {
    await this.resOperateInput.sendKeys(resOperate);
  }

  async getResOperateInput() {
    return await this.resOperateInput.getAttribute('value');
  }

  async setResHrefInput(resHref) {
    await this.resHrefInput.sendKeys(resHref);
  }

  async getResHrefInput() {
    return await this.resHrefInput.getAttribute('value');
  }

  async setResSrcInput(resSrc) {
    await this.resSrcInput.sendKeys(resSrc);
  }

  async getResSrcInput() {
    return await this.resSrcInput.getAttribute('value');
  }

  async setResTextInput(resText) {
    await this.resTextInput.sendKeys(resText);
  }

  async getResTextInput() {
    return await this.resTextInput.getAttribute('value');
  }

  async setResClassInput(resClass) {
    await this.resClassInput.sendKeys(resClass);
  }

  async getResClassInput() {
    return await this.resClassInput.getAttribute('value');
  }

  async setResEffDateInput(resEffDate) {
    await this.resEffDateInput.sendKeys(resEffDate);
  }

  async getResEffDateInput() {
    return await this.resEffDateInput.getAttribute('value');
  }

  async setResExpDateInput(resExpDate) {
    await this.resExpDateInput.sendKeys(resExpDate);
  }

  async getResExpDateInput() {
    return await this.resExpDateInput.getAttribute('value');
  }

  async setResLftInput(resLft) {
    await this.resLftInput.sendKeys(resLft);
  }

  async getResLftInput() {
    return await this.resLftInput.getAttribute('value');
  }

  async setResRgtInput(resRgt) {
    await this.resRgtInput.sendKeys(resRgt);
  }

  async getResRgtInput() {
    return await this.resRgtInput.getAttribute('value');
  }

  async setResLevelInput(resLevel) {
    await this.resLevelInput.sendKeys(resLevel);
  }

  async getResLevelInput() {
    return await this.resLevelInput.getAttribute('value');
  }

  async setResOrderInput(resOrder) {
    await this.resOrderInput.sendKeys(resOrder);
  }

  async getResOrderInput() {
    return await this.resOrderInput.getAttribute('value');
  }

  getLeafInput(timeout?: number) {
    return this.leafInput;
  }
  getResDisabledInput(timeout?: number) {
    return this.resDisabledInput;
  }
  getResCheckedInput(timeout?: number) {
    return this.resCheckedInput;
  }
  getResExpandedInput(timeout?: number) {
    return this.resExpandedInput;
  }
  getResSelectedInput(timeout?: number) {
    return this.resSelectedInput;
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

export class ResourceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-resource-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-resource'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
