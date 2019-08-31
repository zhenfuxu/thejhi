import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GetewaySharedModule } from 'app/shared';
import {
  RoleComponent,
  RoleDetailComponent,
  RoleUpdateComponent,
  RoleDeletePopupComponent,
  RoleDeleteDialogComponent,
  roleRoute,
  rolePopupRoute
} from './';

const ENTITY_STATES = [...roleRoute, ...rolePopupRoute];

@NgModule({
  imports: [GetewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RoleComponent, RoleDetailComponent, RoleUpdateComponent, RoleDeleteDialogComponent, RoleDeletePopupComponent],
  entryComponents: [RoleComponent, RoleUpdateComponent, RoleDeleteDialogComponent, RoleDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GetewayRoleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
