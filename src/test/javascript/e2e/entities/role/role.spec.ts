/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RoleComponentsPage, RoleDeleteDialog, RoleUpdatePage } from './role.page-object';

const expect = chai.expect;

describe('Role e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let roleUpdatePage: RoleUpdatePage;
  let roleComponentsPage: RoleComponentsPage;
  let roleDeleteDialog: RoleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Roles', async () => {
    await navBarPage.goToEntity('role');
    roleComponentsPage = new RoleComponentsPage();
    await browser.wait(ec.visibilityOf(roleComponentsPage.title), 5000);
    expect(await roleComponentsPage.getTitle()).to.eq('getewayApp.role.home.title');
  });

  it('should load create Role page', async () => {
    await roleComponentsPage.clickOnCreateButton();
    roleUpdatePage = new RoleUpdatePage();
    expect(await roleUpdatePage.getPageTitle()).to.eq('getewayApp.role.home.createOrEditLabel');
    await roleUpdatePage.cancel();
  });

  it('should create and save Roles', async () => {
    const nbButtonsBeforeCreate = await roleComponentsPage.countDeleteButtons();

    await roleComponentsPage.clickOnCreateButton();
    await promise.all([
      roleUpdatePage.setRoleNameInput('roleName'),
      roleUpdatePage.setRoleDescriptionInput('roleDescription'),
      roleUpdatePage.setRoleFlagInput('roleFlag'),
      roleUpdatePage.setRoleEffDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      roleUpdatePage.setRoleExpDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
      // roleUpdatePage.resourcesSelectLastOption(),
    ]);
    expect(await roleUpdatePage.getRoleNameInput()).to.eq('roleName', 'Expected RoleName value to be equals to roleName');
    expect(await roleUpdatePage.getRoleDescriptionInput()).to.eq(
      'roleDescription',
      'Expected RoleDescription value to be equals to roleDescription'
    );
    expect(await roleUpdatePage.getRoleFlagInput()).to.eq('roleFlag', 'Expected RoleFlag value to be equals to roleFlag');
    expect(await roleUpdatePage.getRoleEffDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected roleEffDate value to be equals to 2000-12-31'
    );
    expect(await roleUpdatePage.getRoleExpDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected roleExpDate value to be equals to 2000-12-31'
    );
    await roleUpdatePage.save();
    expect(await roleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await roleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Role', async () => {
    const nbButtonsBeforeDelete = await roleComponentsPage.countDeleteButtons();
    await roleComponentsPage.clickOnLastDeleteButton();

    roleDeleteDialog = new RoleDeleteDialog();
    expect(await roleDeleteDialog.getDialogTitle()).to.eq('getewayApp.role.delete.question');
    await roleDeleteDialog.clickOnConfirmButton();

    expect(await roleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
