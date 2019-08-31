import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { GetewaySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [GetewaySharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [GetewaySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GetewaySharedModule {
  static forRoot() {
    return {
      ngModule: GetewaySharedModule
    };
  }
}
