import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GetewaySharedModule } from 'app/shared';
import {
  UserxComponent,
  UserxDetailComponent,
  UserxUpdateComponent,
  UserxDeletePopupComponent,
  UserxDeleteDialogComponent,
  userxRoute,
  userxPopupRoute
} from './';

const ENTITY_STATES = [...userxRoute, ...userxPopupRoute];

@NgModule({
  imports: [GetewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [UserxComponent, UserxDetailComponent, UserxUpdateComponent, UserxDeleteDialogComponent, UserxDeletePopupComponent],
  entryComponents: [UserxComponent, UserxUpdateComponent, UserxDeleteDialogComponent, UserxDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GetewayUserxModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
