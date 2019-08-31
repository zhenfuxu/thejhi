import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserRoleOrganization } from 'app/shared/model/user-role-organization.model';

type EntityResponseType = HttpResponse<IUserRoleOrganization>;
type EntityArrayResponseType = HttpResponse<IUserRoleOrganization[]>;

@Injectable({ providedIn: 'root' })
export class UserRoleOrganizationService {
  public resourceUrl = SERVER_API_URL + 'api/user-role-organizations';

  constructor(protected http: HttpClient) {}

  create(userRoleOrganization: IUserRoleOrganization): Observable<EntityResponseType> {
    return this.http.post<IUserRoleOrganization>(this.resourceUrl, userRoleOrganization, { observe: 'response' });
  }

  update(userRoleOrganization: IUserRoleOrganization): Observable<EntityResponseType> {
    return this.http.put<IUserRoleOrganization>(this.resourceUrl, userRoleOrganization, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserRoleOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserRoleOrganization[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
