/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrganizationComponentsPage, OrganizationDeleteDialog, OrganizationUpdatePage } from './organization.page-object';

const expect = chai.expect;

describe('Organization e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let organizationUpdatePage: OrganizationUpdatePage;
  let organizationComponentsPage: OrganizationComponentsPage;
  let organizationDeleteDialog: OrganizationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Organizations', async () => {
    await navBarPage.goToEntity('organization');
    organizationComponentsPage = new OrganizationComponentsPage();
    await browser.wait(ec.visibilityOf(organizationComponentsPage.title), 5000);
    expect(await organizationComponentsPage.getTitle()).to.eq('getewayApp.organization.home.title');
  });

  it('should load create Organization page', async () => {
    await organizationComponentsPage.clickOnCreateButton();
    organizationUpdatePage = new OrganizationUpdatePage();
    expect(await organizationUpdatePage.getPageTitle()).to.eq('getewayApp.organization.home.createOrEditLabel');
    await organizationUpdatePage.cancel();
  });

  it('should create and save Organizations', async () => {
    const nbButtonsBeforeCreate = await organizationComponentsPage.countDeleteButtons();

    await organizationComponentsPage.clickOnCreateButton();
    await promise.all([
      organizationUpdatePage.setOrgNameInput('orgName'),
      organizationUpdatePage.setOrgCodeInput('orgCode'),
      organizationUpdatePage.setOrgFlagInput('orgFlag'),
      organizationUpdatePage.setOrgAreaCodeInput('orgAreaCode'),
      organizationUpdatePage.setOrgAreaNameInput('orgAreaName'),
      organizationUpdatePage.setOrgDescriptionInput('orgDescription'),
      organizationUpdatePage.setOrgLftInput('5'),
      organizationUpdatePage.setOrgRgtInput('5'),
      organizationUpdatePage.setOrgLevelInput('5'),
      organizationUpdatePage.setOrgOrderInput('5'),
      organizationUpdatePage.upperSelectLastOption()
    ]);
    expect(await organizationUpdatePage.getOrgNameInput()).to.eq('orgName', 'Expected OrgName value to be equals to orgName');
    expect(await organizationUpdatePage.getOrgCodeInput()).to.eq('orgCode', 'Expected OrgCode value to be equals to orgCode');
    expect(await organizationUpdatePage.getOrgFlagInput()).to.eq('orgFlag', 'Expected OrgFlag value to be equals to orgFlag');
    expect(await organizationUpdatePage.getOrgAreaCodeInput()).to.eq(
      'orgAreaCode',
      'Expected OrgAreaCode value to be equals to orgAreaCode'
    );
    expect(await organizationUpdatePage.getOrgAreaNameInput()).to.eq(
      'orgAreaName',
      'Expected OrgAreaName value to be equals to orgAreaName'
    );
    expect(await organizationUpdatePage.getOrgDescriptionInput()).to.eq(
      'orgDescription',
      'Expected OrgDescription value to be equals to orgDescription'
    );
    expect(await organizationUpdatePage.getOrgLftInput()).to.eq('5', 'Expected orgLft value to be equals to 5');
    expect(await organizationUpdatePage.getOrgRgtInput()).to.eq('5', 'Expected orgRgt value to be equals to 5');
    expect(await organizationUpdatePage.getOrgLevelInput()).to.eq('5', 'Expected orgLevel value to be equals to 5');
    expect(await organizationUpdatePage.getOrgOrderInput()).to.eq('5', 'Expected orgOrder value to be equals to 5');
    const selectedLeaf = organizationUpdatePage.getLeafInput();
    if (await selectedLeaf.isSelected()) {
      await organizationUpdatePage.getLeafInput().click();
      expect(await organizationUpdatePage.getLeafInput().isSelected(), 'Expected leaf not to be selected').to.be.false;
    } else {
      await organizationUpdatePage.getLeafInput().click();
      expect(await organizationUpdatePage.getLeafInput().isSelected(), 'Expected leaf to be selected').to.be.true;
    }
    await organizationUpdatePage.save();
    expect(await organizationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await organizationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Organization', async () => {
    const nbButtonsBeforeDelete = await organizationComponentsPage.countDeleteButtons();
    await organizationComponentsPage.clickOnLastDeleteButton();

    organizationDeleteDialog = new OrganizationDeleteDialog();
    expect(await organizationDeleteDialog.getDialogTitle()).to.eq('getewayApp.organization.delete.question');
    await organizationDeleteDialog.clickOnConfirmButton();

    expect(await organizationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
