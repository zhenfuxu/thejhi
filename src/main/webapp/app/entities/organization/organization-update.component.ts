import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOrganization, Organization } from 'app/shared/model/organization.model';
import { OrganizationService } from './organization.service';

@Component({
  selector: 'jhi-organization-update',
  templateUrl: './organization-update.component.html'
})
export class OrganizationUpdateComponent implements OnInit {
  isSaving: boolean;

  organizations: IOrganization[];

  editForm = this.fb.group({
    id: [],
    orgName: [],
    orgCode: [],
    orgFlag: [],
    orgAreaCode: [],
    orgAreaName: [],
    orgDescription: [],
    orgLft: [],
    orgRgt: [],
    orgLevel: [],
    orgOrder: [],
    leaf: [],
    upperId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ organization }) => {
      this.updateForm(organization);
    });
    this.organizationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrganization[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrganization[]>) => response.body)
      )
      .subscribe((res: IOrganization[]) => (this.organizations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(organization: IOrganization) {
    this.editForm.patchValue({
      id: organization.id,
      orgName: organization.orgName,
      orgCode: organization.orgCode,
      orgFlag: organization.orgFlag,
      orgAreaCode: organization.orgAreaCode,
      orgAreaName: organization.orgAreaName,
      orgDescription: organization.orgDescription,
      orgLft: organization.orgLft,
      orgRgt: organization.orgRgt,
      orgLevel: organization.orgLevel,
      orgOrder: organization.orgOrder,
      leaf: organization.leaf,
      upperId: organization.upperId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const organization = this.createFromForm();
    if (organization.id !== undefined) {
      this.subscribeToSaveResponse(this.organizationService.update(organization));
    } else {
      this.subscribeToSaveResponse(this.organizationService.create(organization));
    }
  }

  private createFromForm(): IOrganization {
    return {
      ...new Organization(),
      id: this.editForm.get(['id']).value,
      orgName: this.editForm.get(['orgName']).value,
      orgCode: this.editForm.get(['orgCode']).value,
      orgFlag: this.editForm.get(['orgFlag']).value,
      orgAreaCode: this.editForm.get(['orgAreaCode']).value,
      orgAreaName: this.editForm.get(['orgAreaName']).value,
      orgDescription: this.editForm.get(['orgDescription']).value,
      orgLft: this.editForm.get(['orgLft']).value,
      orgRgt: this.editForm.get(['orgRgt']).value,
      orgLevel: this.editForm.get(['orgLevel']).value,
      orgOrder: this.editForm.get(['orgOrder']).value,
      leaf: this.editForm.get(['leaf']).value,
      upperId: this.editForm.get(['upperId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganization>>) {
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
}
