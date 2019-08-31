import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Userx } from 'app/shared/model/userx.model';
import { UserxService } from './userx.service';
import { UserxComponent } from './userx.component';
import { UserxDetailComponent } from './userx-detail.component';
import { UserxUpdateComponent } from './userx-update.component';
import { UserxDeletePopupComponent } from './userx-delete-dialog.component';
import { IUserx } from 'app/shared/model/userx.model';

@Injectable({ providedIn: 'root' })
export class UserxResolve implements Resolve<IUserx> {
  constructor(private service: UserxService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserx> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Userx>) => response.ok),
        map((userx: HttpResponse<Userx>) => userx.body)
      );
    }
    return of(new Userx());
  }
}

export const userxRoute: Routes = [
  {
    path: '',
    component: UserxComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'getewayApp.userx.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserxDetailComponent,
    resolve: {
      userx: UserxResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getewayApp.userx.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserxUpdateComponent,
    resolve: {
      userx: UserxResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getewayApp.userx.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserxUpdateComponent,
    resolve: {
      userx: UserxResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getewayApp.userx.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userxPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserxDeletePopupComponent,
    resolve: {
      userx: UserxResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'getewayApp.userx.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
