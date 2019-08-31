import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRole, Role } from 'app/shared/model/role.model';
import { RoleService } from './role.service';
import { IResource } from 'app/shared/model/resource.model';
import { ResourceService } from 'app/entities/resource';

@Component({
  selector: 'jhi-role-update',
  templateUrl: './role-update.component.html'
})
export class RoleUpdateComponent implements OnInit {
  isSaving: boolean;

  resources: IResource[];

  editForm = this.fb.group({
    id: [],
    roleName: [],
    roleDescription: [],
    roleFlag: [],
    roleEffDate: [],
    roleExpDate: [],
    resources: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected roleService: RoleService,
    protected resourceService: ResourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ role }) => {
      this.updateForm(role);
    });
    this.resourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IResource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IResource[]>) => response.body)
      )
      .subscribe((res: IResource[]) => (this.resources = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(role: IRole) {
    this.editForm.patchValue({
      id: role.id,
      roleName: role.roleName,
      roleDescription: role.roleDescription,
      roleFlag: role.roleFlag,
      roleEffDate: role.roleEffDate != null ? role.roleEffDate.format(DATE_TIME_FORMAT) : null,
      roleExpDate: role.roleExpDate != null ? role.roleExpDate.format(DATE_TIME_FORMAT) : null,
      resources: role.resources
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const role = this.createFromForm();
    if (role.id !== undefined) {
      this.subscribeToSaveResponse(this.roleService.update(role));
    } else {
      this.subscribeToSaveResponse(this.roleService.create(role));
    }
  }

  private createFromForm(): IRole {
    return {
      ...new Role(),
      id: this.editForm.get(['id']).value,
      roleName: this.editForm.get(['roleName']).value,
      roleDescription: this.editForm.get(['roleDescription']).value,
      roleFlag: this.editForm.get(['roleFlag']).value,
      roleEffDate:
        this.editForm.get(['roleEffDate']).value != null ? moment(this.editForm.get(['roleEffDate']).value, DATE_TIME_FORMAT) : undefined,
      roleExpDate:
        this.editForm.get(['roleExpDate']).value != null ? moment(this.editForm.get(['roleExpDate']).value, DATE_TIME_FORMAT) : undefined,
      resources: this.editForm.get(['resources']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRole>>) {
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

  trackResourceById(index: number, item: IResource) {
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
