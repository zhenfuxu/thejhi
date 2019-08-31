import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GetewaySharedModule } from 'app/shared';
import {
  UserRoleOrganizationComponent,
  UserRoleOrganizationDetailComponent,
  UserRoleOrganizationUpdateComponent,
  UserRoleOrganizationDeletePopupComponent,
  UserRoleOrganizationDeleteDialogComponent,
  userRoleOrganizationRoute,
  userRoleOrganizationPopupRoute
} from './';

const ENTITY_STATES = [...userRoleOrganizationRoute, ...userRoleOrganizationPopupRoute];

@NgModule({
  imports: [GetewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserRoleOrganizationComponent,
    UserRoleOrganizationDetailComponent,
    UserRoleOrganizationUpdateComponent,
    UserRoleOrganizationDeleteDialogComponent,
    UserRoleOrganizationDeletePopupComponent
  ],
  entryComponents: [
    UserRoleOrganizationComponent,
    UserRoleOrganizationUpdateComponent,
    UserRoleOrganizationDeleteDialogComponent,
    UserRoleOrganizationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GetewayUserRoleOrganizationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
