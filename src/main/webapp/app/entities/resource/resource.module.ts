import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GetewaySharedModule } from 'app/shared';
import {
  ResourceComponent,
  ResourceDetailComponent,
  ResourceUpdateComponent,
  ResourceDeletePopupComponent,
  ResourceDeleteDialogComponent,
  resourceRoute,
  resourcePopupRoute
} from './';

const ENTITY_STATES = [...resourceRoute, ...resourcePopupRoute];

@NgModule({
  imports: [GetewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ResourceComponent,
    ResourceDetailComponent,
    ResourceUpdateComponent,
    ResourceDeleteDialogComponent,
    ResourceDeletePopupComponent
  ],
  entryComponents: [ResourceComponent, ResourceUpdateComponent, ResourceDeleteDialogComponent, ResourceDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GetewayResourceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
