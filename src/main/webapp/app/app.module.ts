import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { GetewaySharedModule } from 'app/shared/shared.module';
import { GetewayCoreModule } from 'app/core/core.module';
import { GetewayAppRoutingModule } from './app-routing.module';
import { GetewayHomeModule } from './home/home.module';
import { GetewayEntityModule } from './entities/entity.module';
import { CoreuiModule } from './coreui/app.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    GetewaySharedModule,
    GetewayCoreModule,
    GetewayHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    GetewayEntityModule,
    CoreuiModule,
    GetewayAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class GetewayAppModule {}
