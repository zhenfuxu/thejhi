import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IUserx, Userx } from 'app/shared/model/userx.model';
import { UserxService } from './userx.service';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';

@Component({
  selector: 'jhi-userx-update',
  templateUrl: './userx-update.component.html'
})
export class UserxUpdateComponent implements OnInit {
  isSaving: boolean;

  organizations: IOrganization[];

  roles: IRole[];

  editForm = this.fb.group({
    id: [],
    login: [null, [Validators.required, Validators.minLength(50)]],
    password: [null, [Validators.minLength(60)]],
    firstName: [null, [Validators.maxLength(50)]],
    lastName: [null, [Validators.maxLength(50)]],
    email: [],
    activated: [],
    langKey: [],
    imageUrl: [],
    activationKey: [],
    resetKey: [],
    resetDate: [],
    organizationId: [],
    roles: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userxService: UserxService,
    protected organizationService: OrganizationService,
    protected roleService: RoleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userx }) => {
      this.updateForm(userx);
    });
    this.organizationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrganization[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrganization[]>) => response.body)
      )
      .subscribe((res: IOrganization[]) => (this.organizations = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.roleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRole[]>) => response.body)
      )
      .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(userx: IUserx) {
    this.editForm.patchValue({
      id: userx.id,
      login: userx.login,
      password: userx.password,
      firstName: userx.firstName,
      lastName: userx.lastName,
      email: userx.email,
      activated: userx.activated,
      langKey: userx.langKey,
      imageUrl: userx.imageUrl,
      activationKey: userx.activationKey,
      resetKey: userx.resetKey,
      resetDate: userx.resetDate != null ? userx.resetDate.format(DATE_TIME_FORMAT) : null,
      organizationId: userx.organizationId,
      roles: userx.roles
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userx = this.createFromForm();
    if (userx.id !== undefined) {
      this.subscribeToSaveResponse(this.userxService.update(userx));
    } else {
      this.subscribeToSaveResponse(this.userxService.create(userx));
    }
  }

  private createFromForm(): IUserx {
    return {
      ...new Userx(),
      id: this.editForm.get(['id']).value,
      login: this.editForm.get(['login']).value,
      password: this.editForm.get(['password']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      email: this.editForm.get(['email']).value,
      activated: this.editForm.get(['activated']).value,
      langKey: this.editForm.get(['langKey']).value,
      imageUrl: this.editForm.get(['imageUrl']).value,
      activationKey: this.editForm.get(['activationKey']).value,
      resetKey: this.editForm.get(['resetKey']).value,
      resetDate:
        this.editForm.get(['resetDate']).value != null ? moment(this.editForm.get(['resetDate']).value, DATE_TIME_FORMAT) : undefined,
      organizationId: this.editForm.get(['organizationId']).value,
      roles: this.editForm.get(['roles']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserx>>) {
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

  trackOrganizationById(index: number, item: IOrganization) {
    return item.id;
  }

  trackRoleById(index: number, item: IRole) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
