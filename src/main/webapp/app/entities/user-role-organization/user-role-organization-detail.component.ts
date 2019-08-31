import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserRoleOrganization } from 'app/shared/model/user-role-organization.model';

@Component({
  selector: 'jhi-user-role-organization-detail',
  templateUrl: './user-role-organization-detail.component.html'
})
export class UserRoleOrganizationDetailComponent implements OnInit {
  userRoleOrganization: IUserRoleOrganization;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userRoleOrganization }) => {
      this.userRoleOrganization = userRoleOrganization;
    });
  }

  previousState() {
    window.history.back();
  }
}
