import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserx } from 'app/shared/model/userx.model';

type EntityResponseType = HttpResponse<IUserx>;
type EntityArrayResponseType = HttpResponse<IUserx[]>;

@Injectable({ providedIn: 'root' })
export class UserxService {
  public resourceUrl = SERVER_API_URL + 'api/userxes';

  constructor(protected http: HttpClient) {}

  create(userx: IUserx): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userx);
    return this.http
      .post<IUserx>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userx: IUserx): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userx);
    return this.http
      .put<IUserx>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserx>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserx[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userx: IUserx): IUserx {
    const copy: IUserx = Object.assign({}, userx, {
      resetDate: userx.resetDate != null && userx.resetDate.isValid() ? userx.resetDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.resetDate = res.body.resetDate != null ? moment(res.body.resetDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userx: IUserx) => {
        userx.resetDate = userx.resetDate != null ? moment(userx.resetDate) : null;
      });
    }
    return res;
  }
}
