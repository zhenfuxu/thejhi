import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IResource, Resource } from 'app/shared/model/resource.model';
import { ResourceService } from './resource.service';

@Component({
  selector: 'jhi-resource-update',
  templateUrl: './resource-update.component.html'
})
export class ResourceUpdateComponent implements OnInit {
  isSaving: boolean;

  resources: IResource[];

  editForm = this.fb.group({
    id: [],
    resRouterLink: [],
    resDescription: [],
    resFlag: [],
    resOperate: [],
    resHref: [],
    resSrc: [],
    resText: [],
    resClass: [],
    resEffDate: [],
    resExpDate: [],
    resLft: [],
    resRgt: [],
    resLevel: [],
    resOrder: [],
    leaf: [],
    resDisabled: [],
    resChecked: [],
    resExpanded: [],
    resSelected: [],
    upperId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected resourceService: ResourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ resource }) => {
      this.updateForm(resource);
    });
    this.resourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IResource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IResource[]>) => response.body)
      )
      .subscribe((res: IResource[]) => (this.resources = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(resource: IResource) {
    this.editForm.patchValue({
      id: resource.id,
      resRouterLink: resource.resRouterLink,
      resDescription: resource.resDescription,
      resFlag: resource.resFlag,
      resOperate: resource.resOperate,
      resHref: resource.resHref,
      resSrc: resource.resSrc,
      resText: resource.resText,
      resClass: resource.resClass,
      resEffDate: resource.resEffDate != null ? resource.resEffDate.format(DATE_TIME_FORMAT) : null,
      resExpDate: resource.resExpDate != null ? resource.resExpDate.format(DATE_TIME_FORMAT) : null,
      resLft: resource.resLft,
      resRgt: resource.resRgt,
      resLevel: resource.resLevel,
      resOrder: resource.resOrder,
      leaf: resource.leaf,
      resDisabled: resource.resDisabled,
      resChecked: resource.resChecked,
      resExpanded: resource.resExpanded,
      resSelected: resource.resSelected,
      upperId: resource.upperId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const resource = this.createFromForm();
    if (resource.id !== undefined) {
      this.subscribeToSaveResponse(this.resourceService.update(resource));
    } else {
      this.subscribeToSaveResponse(this.resourceService.create(resource));
    }
  }

  private createFromForm(): IResource {
    return {
      ...new Resource(),
      id: this.editForm.get(['id']).value,
      resRouterLink: this.editForm.get(['resRouterLink']).value,
      resDescription: this.editForm.get(['resDescription']).value,
      resFlag: this.editForm.get(['resFlag']).value,
      resOperate: this.editForm.get(['resOperate']).value,
      resHref: this.editForm.get(['resHref']).value,
      resSrc: this.editForm.get(['resSrc']).value,
      resText: this.editForm.get(['resText']).value,
      resClass: this.editForm.get(['resClass']).value,
      resEffDate:
        this.editForm.get(['resEffDate']).value != null ? moment(this.editForm.get(['resEffDate']).value, DATE_TIME_FORMAT) : undefined,
      resExpDate:
        this.editForm.get(['resExpDate']).value != null ? moment(this.editForm.get(['resExpDate']).value, DATE_TIME_FORMAT) : undefined,
      resLft: this.editForm.get(['resLft']).value,
      resRgt: this.editForm.get(['resRgt']).value,
      resLevel: this.editForm.get(['resLevel']).value,
      resOrder: this.editForm.get(['resOrder']).value,
      leaf: this.editForm.get(['leaf']).value,
      resDisabled: this.editForm.get(['resDisabled']).value,
      resChecked: this.editForm.get(['resChecked']).value,
      resExpanded: this.editForm.get(['resExpanded']).value,
      resSelected: this.editForm.get(['resSelected']).value,
      upperId: this.editForm.get(['upperId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResource>>) {
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
}
