/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ResourceComponentsPage, ResourceDeleteDialog, ResourceUpdatePage } from './resource.page-object';

const expect = chai.expect;

describe('Resource e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let resourceUpdatePage: ResourceUpdatePage;
  let resourceComponentsPage: ResourceComponentsPage;
  let resourceDeleteDialog: ResourceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Resources', async () => {
    await navBarPage.goToEntity('resource');
    resourceComponentsPage = new ResourceComponentsPage();
    await browser.wait(ec.visibilityOf(resourceComponentsPage.title), 5000);
    expect(await resourceComponentsPage.getTitle()).to.eq('getewayApp.resource.home.title');
  });

  it('should load create Resource page', async () => {
    await resourceComponentsPage.clickOnCreateButton();
    resourceUpdatePage = new ResourceUpdatePage();
    expect(await resourceUpdatePage.getPageTitle()).to.eq('getewayApp.resource.home.createOrEditLabel');
    await resourceUpdatePage.cancel();
  });

  it('should create and save Resources', async () => {
    const nbButtonsBeforeCreate = await resourceComponentsPage.countDeleteButtons();

    await resourceComponentsPage.clickOnCreateButton();
    await promise.all([
      resourceUpdatePage.setResRouterLinkInput('resRouterLink'),
      resourceUpdatePage.setResDescriptionInput('resDescription'),
      resourceUpdatePage.setResFlagInput('resFlag'),
      resourceUpdatePage.setResOperateInput('resOperate'),
      resourceUpdatePage.setResHrefInput('resHref'),
      resourceUpdatePage.setResSrcInput('resSrc'),
      resourceUpdatePage.setResTextInput('resText'),
      resourceUpdatePage.setResClassInput('resClass'),
      resourceUpdatePage.setResEffDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      resourceUpdatePage.setResExpDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      resourceUpdatePage.setResLftInput('5'),
      resourceUpdatePage.setResRgtInput('5'),
      resourceUpdatePage.setResLevelInput('5'),
      resourceUpdatePage.setResOrderInput('5'),
      resourceUpdatePage.upperSelectLastOption()
    ]);
    expect(await resourceUpdatePage.getResRouterLinkInput()).to.eq(
      'resRouterLink',
      'Expected ResRouterLink value to be equals to resRouterLink'
    );
    expect(await resourceUpdatePage.getResDescriptionInput()).to.eq(
      'resDescription',
      'Expected ResDescription value to be equals to resDescription'
    );
    expect(await resourceUpdatePage.getResFlagInput()).to.eq('resFlag', 'Expected ResFlag value to be equals to resFlag');
    expect(await resourceUpdatePage.getResOperateInput()).to.eq('resOperate', 'Expected ResOperate value to be equals to resOperate');
    expect(await resourceUpdatePage.getResHrefInput()).to.eq('resHref', 'Expected ResHref value to be equals to resHref');
    expect(await resourceUpdatePage.getResSrcInput()).to.eq('resSrc', 'Expected ResSrc value to be equals to resSrc');
    expect(await resourceUpdatePage.getResTextInput()).to.eq('resText', 'Expected ResText value to be equals to resText');
    expect(await resourceUpdatePage.getResClassInput()).to.eq('resClass', 'Expected ResClass value to be equals to resClass');
    expect(await resourceUpdatePage.getResEffDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected resEffDate value to be equals to 2000-12-31'
    );
    expect(await resourceUpdatePage.getResExpDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected resExpDate value to be equals to 2000-12-31'
    );
    expect(await resourceUpdatePage.getResLftInput()).to.eq('5', 'Expected resLft value to be equals to 5');
    expect(await resourceUpdatePage.getResRgtInput()).to.eq('5', 'Expected resRgt value to be equals to 5');
    expect(await resourceUpdatePage.getResLevelInput()).to.eq('5', 'Expected resLevel value to be equals to 5');
    expect(await resourceUpdatePage.getResOrderInput()).to.eq('5', 'Expected resOrder value to be equals to 5');
    const selectedLeaf = resourceUpdatePage.getLeafInput();
    if (await selectedLeaf.isSelected()) {
      await resourceUpdatePage.getLeafInput().click();
      expect(await resourceUpdatePage.getLeafInput().isSelected(), 'Expected leaf not to be selected').to.be.false;
    } else {
      await resourceUpdatePage.getLeafInput().click();
      expect(await resourceUpdatePage.getLeafInput().isSelected(), 'Expected leaf to be selected').to.be.true;
    }
    const selectedResDisabled = resourceUpdatePage.getResDisabledInput();
    if (await selectedResDisabled.isSelected()) {
      await resourceUpdatePage.getResDisabledInput().click();
      expect(await resourceUpdatePage.getResDisabledInput().isSelected(), 'Expected resDisabled not to be selected').to.be.false;
    } else {
      await resourceUpdatePage.getResDisabledInput().click();
      expect(await resourceUpdatePage.getResDisabledInput().isSelected(), 'Expected resDisabled to be selected').to.be.true;
    }
    const selectedResChecked = resourceUpdatePage.getResCheckedInput();
    if (await selectedResChecked.isSelected()) {
      await resourceUpdatePage.getResCheckedInput().click();
      expect(await resourceUpdatePage.getResCheckedInput().isSelected(), 'Expected resChecked not to be selected').to.be.false;
    } else {
      await resourceUpdatePage.getResCheckedInput().click();
      expect(await resourceUpdatePage.getResCheckedInput().isSelected(), 'Expected resChecked to be selected').to.be.true;
    }
    const selectedResExpanded = resourceUpdatePage.getResExpandedInput();
    if (await selectedResExpanded.isSelected()) {
      await resourceUpdatePage.getResExpandedInput().click();
      expect(await resourceUpdatePage.getResExpandedInput().isSelected(), 'Expected resExpanded not to be selected').to.be.false;
    } else {
      await resourceUpdatePage.getResExpandedInput().click();
      expect(await resourceUpdatePage.getResExpandedInput().isSelected(), 'Expected resExpanded to be selected').to.be.true;
    }
    const selectedResSelected = resourceUpdatePage.getResSelectedInput();
    if (await selectedResSelected.isSelected()) {
      await resourceUpdatePage.getResSelectedInput().click();
      expect(await resourceUpdatePage.getResSelectedInput().isSelected(), 'Expected resSelected not to be selected').to.be.false;
    } else {
      await resourceUpdatePage.getResSelectedInput().click();
      expect(await resourceUpdatePage.getResSelectedInput().isSelected(), 'Expected resSelected to be selected').to.be.true;
    }
    await resourceUpdatePage.save();
    expect(await resourceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await resourceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Resource', async () => {
    const nbButtonsBeforeDelete = await resourceComponentsPage.countDeleteButtons();
    await resourceComponentsPage.clickOnLastDeleteButton();

    resourceDeleteDialog = new ResourceDeleteDialog();
    expect(await resourceDeleteDialog.getDialogTitle()).to.eq('getewayApp.resource.delete.question');
    await resourceDeleteDialog.clickOnConfirmButton();

    expect(await resourceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
