import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserRoleOrganization, UserRoleOrganization } from 'app/shared/model/user-role-organization.model';
import { UserRoleOrganizationService } from './user-role-organization.service';
import { IUserx } from 'app/shared/model/userx.model';
import { UserxService } from 'app/entities/userx';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';

@Component({
  selector: 'jhi-user-role-organization-update',
  templateUrl: './user-role-organization-update.component.html'
})
export class UserRoleOrganizationUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUserx[];

  roles: IRole[];

  organizations: IOrganization[];

  editForm = this.fb.group({
    id: [],
    userName: [],
    roleName: [],
    orgName: [],
    userId: [null, Validators.required],
    roleId: [null, Validators.required],
    organizationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userRoleOrganizationService: UserRoleOrganizationService,
    protected userxService: UserxService,
    protected roleService: RoleService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userRoleOrganization }) => {
      this.updateForm(userRoleOrganization);
    });
    this.userxService
      .query({ 'userRoleOrganizationId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IUserx[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserx[]>) => response.body)
      )
      .subscribe(
        (res: IUserx[]) => {
          if (!this.editForm.get('userId').value) {
            this.users = res;
          } else {
            this.userxService
              .find(this.editForm.get('userId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IUserx>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IUserx>) => subResponse.body)
              )
              .subscribe(
                (subRes: IUserx) => (this.users = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.roleService
      .query({ 'userRoleOrganizationId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRole[]>) => response.body)
      )
      .subscribe(
        (res: IRole[]) => {
          if (!this.editForm.get('roleId').value) {
            this.roles = res;
          } else {
            this.roleService
              .find(this.editForm.get('roleId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IRole>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IRole>) => subResponse.body)
              )
              .subscribe(
                (subRes: IRole) => (this.roles = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.organizationService
      .query({ 'userRoleOrganizationId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IOrganization[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrganization[]>) => response.body)
      )
      .subscribe(
        (res: IOrganization[]) => {
          if (!this.editForm.get('organizationId').value) {
            this.organizations = res;
          } else {
            this.organizationService
              .find(this.editForm.get('organizationId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IOrganization>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IOrganization>) => subResponse.body)
              )
              .subscribe(
                (subRes: IOrganization) => (this.organizations = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(userRoleOrganization: IUserRoleOrganization) {
    this.editForm.patchValue({
      id: userRoleOrganization.id,
      userName: userRoleOrganization.userName,
      roleName: userRoleOrganization.roleName,
      orgName: userRoleOrganization.orgName,
      userId: userRoleOrganization.userId,
      roleId: userRoleOrganization.roleId,
      organizationId: userRoleOrganization.organizationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userRoleOrganization = this.createFromForm();
    if (userRoleOrganization.id !== undefined) {
      this.subscribeToSaveResponse(this.userRoleOrganizationService.update(userRoleOrganization));
    } else {
      this.subscribeToSaveResponse(this.userRoleOrganizationService.create(userRoleOrganization));
    }
  }

  private createFromForm(): IUserRoleOrganization {
    return {
      ...new UserRoleOrganization(),
      id: this.editForm.get(['id']).value,
      userName: this.editForm.get(['userName']).value,
      roleName: this.editForm.get(['roleName']).value,
      orgName: this.editForm.get(['orgName']).value,
      userId: this.editForm.get(['userId']).value,
      roleId: this.editForm.get(['roleId']).value,
      organizationId: this.editForm.get(['organizationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserRoleOrganization>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserxById(index: number, item: IUserx) {
    return item.id;
  }

  trackRoleById(index: number, item: IRole) {
    return item.id;
  }

  trackOrganizationById(index: number, item: IOrganization) {
    return item.id;
  }
}
