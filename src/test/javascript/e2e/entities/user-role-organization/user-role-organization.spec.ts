/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserRoleOrganizationComponentsPage,
  UserRoleOrganizationDeleteDialog,
  UserRoleOrganizationUpdatePage
} from './user-role-organization.page-object';

const expect = chai.expect;

describe('UserRoleOrganization e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userRoleOrganizationUpdatePage: UserRoleOrganizationUpdatePage;
  let userRoleOrganizationComponentsPage: UserRoleOrganizationComponentsPage;
  /*let userRoleOrganizationDeleteDialog: UserRoleOrganizationDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserRoleOrganizations', async () => {
    await navBarPage.goToEntity('user-role-organization');
    userRoleOrganizationComponentsPage = new UserRoleOrganizationComponentsPage();
    await browser.wait(ec.visibilityOf(userRoleOrganizationComponentsPage.title), 5000);
    expect(await userRoleOrganizationComponentsPage.getTitle()).to.eq('getewayApp.userRoleOrganization.home.title');
  });

  it('should load create UserRoleOrganization page', async () => {
    await userRoleOrganizationComponentsPage.clickOnCreateButton();
    userRoleOrganizationUpdatePage = new UserRoleOrganizationUpdatePage();
    expect(await userRoleOrganizationUpdatePage.getPageTitle()).to.eq('getewayApp.userRoleOrganization.home.createOrEditLabel');
    await userRoleOrganizationUpdatePage.cancel();
  });

  /* it('should create and save UserRoleOrganizations', async () => {
        const nbButtonsBeforeCreate = await userRoleOrganizationComponentsPage.countDeleteButtons();

        await userRoleOrganizationComponentsPage.clickOnCreateButton();
        await promise.all([
            userRoleOrganizationUpdatePage.setUserNameInput('userName'),
            userRoleOrganizationUpdatePage.setRoleNameInput('roleName'),
            userRoleOrganizationUpdatePage.setOrgNameInput('orgName'),
            userRoleOrganizationUpdatePage.userSelectLastOption(),
            userRoleOrganizationUpdatePage.roleSelectLastOption(),
            userRoleOrganizationUpdatePage.organizationSelectLastOption(),
        ]);
        expect(await userRoleOrganizationUpdatePage.getUserNameInput()).to.eq('userName', 'Expected UserName value to be equals to userName');
        expect(await userRoleOrganizationUpdatePage.getRoleNameInput()).to.eq('roleName', 'Expected RoleName value to be equals to roleName');
        expect(await userRoleOrganizationUpdatePage.getOrgNameInput()).to.eq('orgName', 'Expected OrgName value to be equals to orgName');
        await userRoleOrganizationUpdatePage.save();
        expect(await userRoleOrganizationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userRoleOrganizationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last UserRoleOrganization', async () => {
        const nbButtonsBeforeDelete = await userRoleOrganizationComponentsPage.countDeleteButtons();
        await userRoleOrganizationComponentsPage.clickOnLastDeleteButton();

        userRoleOrganizationDeleteDialog = new UserRoleOrganizationDeleteDialog();
        expect(await userRoleOrganizationDeleteDialog.getDialogTitle())
            .to.eq('getewayApp.userRoleOrganization.delete.question');
        await userRoleOrganizationDeleteDialog.clickOnConfirmButton();

        expect(await userRoleOrganizationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
