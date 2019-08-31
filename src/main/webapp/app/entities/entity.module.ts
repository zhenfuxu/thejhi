import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'organization',
        loadChildren: () => import('./organization/organization.module').then(m => m.GetewayOrganizationModule)
      },
      {
        path: 'resource',
        loadChildren: () => import('./resource/resource.module').then(m => m.GetewayResourceModule)
      },
      {
        path: 'role',
        loadChildren: () => import('./role/role.module').then(m => m.GetewayRoleModule)
      },
      {
        path: 'userx',
        loadChildren: () => import('./userx/userx.module').then(m => m.GetewayUserxModule)
      },
      {
        path: 'user-role-organization',
        loadChildren: () => import('./user-role-organization/user-role-organization.module').then(m => m.GetewayUserRoleOrganizationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GetewayEntityModule {}
