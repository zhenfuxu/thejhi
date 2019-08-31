/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UserxComponentsPage, UserxDeleteDialog, UserxUpdatePage } from './userx.page-object';

const expect = chai.expect;

describe('Userx e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userxUpdatePage: UserxUpdatePage;
  let userxComponentsPage: UserxComponentsPage;
  let userxDeleteDialog: UserxDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Userxes', async () => {
    await navBarPage.goToEntity('userx');
    userxComponentsPage = new UserxComponentsPage();
    await browser.wait(ec.visibilityOf(userxComponentsPage.title), 5000);
    expect(await userxComponentsPage.getTitle()).to.eq('getewayApp.userx.home.title');
  });

  it('should load create Userx page', async () => {
    await userxComponentsPage.clickOnCreateButton();
    userxUpdatePage = new UserxUpdatePage();
    expect(await userxUpdatePage.getPageTitle()).to.eq('getewayApp.userx.home.createOrEditLabel');
    await userxUpdatePage.cancel();
  });

  it('should create and save Userxes', async () => {
    const nbButtonsBeforeCreate = await userxComponentsPage.countDeleteButtons();

    await userxComponentsPage.clickOnCreateButton();
    await promise.all([
      userxUpdatePage.setLoginInput('login'),
      userxUpdatePage.setPasswordInput('password'),
      userxUpdatePage.setFirstNameInput('firstName'),
      userxUpdatePage.setLastNameInput('lastName'),
      userxUpdatePage.setEmailInput('email'),
      userxUpdatePage.setLangKeyInput('langKey'),
      userxUpdatePage.setImageUrlInput('imageUrl'),
      userxUpdatePage.setActivationKeyInput('activationKey'),
      userxUpdatePage.setResetKeyInput('resetKey'),
      userxUpdatePage.setResetDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      userxUpdatePage.organizationSelectLastOption()
      // userxUpdatePage.rolesSelectLastOption(),
    ]);
    expect(await userxUpdatePage.getLoginInput()).to.eq('login', 'Expected Login value to be equals to login');
    expect(await userxUpdatePage.getPasswordInput()).to.eq('password', 'Expected Password value to be equals to password');
    expect(await userxUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await userxUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await userxUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    const selectedActivated = userxUpdatePage.getActivatedInput();
    if (await selectedActivated.isSelected()) {
      await userxUpdatePage.getActivatedInput().click();
      expect(await userxUpdatePage.getActivatedInput().isSelected(), 'Expected activated not to be selected').to.be.false;
    } else {
      await userxUpdatePage.getActivatedInput().click();
      expect(await userxUpdatePage.getActivatedInput().isSelected(), 'Expected activated to be selected').to.be.true;
    }
    expect(await userxUpdatePage.getLangKeyInput()).to.eq('langKey', 'Expected LangKey value to be equals to langKey');
    expect(await userxUpdatePage.getImageUrlInput()).to.eq('imageUrl', 'Expected ImageUrl value to be equals to imageUrl');
    expect(await userxUpdatePage.getActivationKeyInput()).to.eq(
      'activationKey',
      'Expected ActivationKey value to be equals to activationKey'
    );
    expect(await userxUpdatePage.getResetKeyInput()).to.eq('resetKey', 'Expected ResetKey value to be equals to resetKey');
    expect(await userxUpdatePage.getResetDateInput()).to.contain('2001-01-01T02:30', 'Expected resetDate value to be equals to 2000-12-31');
    await userxUpdatePage.save();
    expect(await userxUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await userxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Userx', async () => {
    const nbButtonsBeforeDelete = await userxComponentsPage.countDeleteButtons();
    await userxComponentsPage.clickOnLastDeleteButton();

    userxDeleteDialog = new UserxDeleteDialog();
    expect(await userxDeleteDialog.getDialogTitle()).to.eq('getewayApp.userx.delete.question');
    await userxDeleteDialog.clickOnConfirmButton();

    expect(await userxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
