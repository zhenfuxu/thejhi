import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserRoleOrganization } from 'app/shared/model/user-role-organization.model';
import { UserRoleOrganizationService } from './user-role-organization.service';
import { UserRoleOrganizationComponent } from './user-role-organization.component';
import { UserRoleOrganizationDetailComponent } from './user-role-organization-detail.component';
import { UserRoleOrganizationUpdateComponent } from './user-role-organization-update.component';
import { UserRoleOrganizationDeletePopupComponent } from './user-role-organization-delete-dialog.component';
import { IUserRoleOrganization } from 'app/shared/model/user-role-organization.model';

@Injectable({ providedIn: 'root' })
export class UserRoleOrganizationResolve implements Resolve<IUserRoleOrganization> {
  constructor(private service: UserRoleOrganizationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserRoleOrganization> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserRoleOrganization>) => response.ok),
        map((userRoleOrganization: HttpResponse<UserRoleOrganization>) => userRoleOrganization.body)
      );
    }
    return of(new UserRoleOrganization());
  }
}

export const userRoleOrganizationRoute: Routes = [
  {
    path: '',
    component: UserRoleOrganizationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'getewayApp.userRoleOrganization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserRoleOrganizationDetailComponent,
    resolve: {
      userRoleOrganization: UserRoleOrganizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getewayApp.userRoleOrganization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserRoleOrganizationUpdateComponent,
    resolve: {
      userRoleOrganization: UserRoleOrganizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getewayApp.userRoleOrganization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserRoleOrganizationUpdateComponent,
    resolve: {
      userRoleOrganization: UserRoleOrganizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getewayApp.userRoleOrganization.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userRoleOrganizationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserRoleOrganizationDeletePopupComponent,
    resolve: {
      userRoleOrganization: UserRoleOrganizationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getewayApp.userRoleOrganization.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
