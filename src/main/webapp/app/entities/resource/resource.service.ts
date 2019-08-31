import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResource } from 'app/shared/model/resource.model';

type EntityResponseType = HttpResponse<IResource>;
type EntityArrayResponseType = HttpResponse<IResource[]>;

@Injectable({ providedIn: 'root' })
export class ResourceService {
  public resourceUrl = SERVER_API_URL + 'api/resources';

  constructor(protected http: HttpClient) {}

  create(resource: IResource): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resource);
    return this.http
      .post<IResource>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(resource: IResource): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resource);
    return this.http
      .put<IResource>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResource>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResource[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(resource: IResource): IResource {
    const copy: IResource = Object.assign({}, resource, {
      resEffDate: resource.resEffDate != null && resource.resEffDate.isValid() ? resource.resEffDate.toJSON() : null,
      resExpDate: resource.resExpDate != null && resource.resExpDate.isValid() ? resource.resExpDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.resEffDate = res.body.resEffDate != null ? moment(res.body.resEffDate) : null;
      res.body.resExpDate = res.body.resExpDate != null ? moment(res.body.resExpDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((resource: IResource) => {
        resource.resEffDate = resource.resEffDate != null ? moment(resource.resEffDate) : null;
        resource.resExpDate = resource.resExpDate != null ? moment(resource.resExpDate) : null;
      });
    }
    return res;
  }
}
