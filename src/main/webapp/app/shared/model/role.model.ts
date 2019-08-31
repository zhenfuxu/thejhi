import { Moment } from 'moment';
import { IResource } from 'app/shared/model/resource.model';

export interface IRole {
  id?: number;
  roleName?: string;
  roleDescription?: string;
  roleFlag?: string;
  roleEffDate?: Moment;
  roleExpDate?: Moment;
  resources?: IResource[];
}

export class Role implements IRole {
  constructor(
    public id?: number,
    public roleName?: string,
    public roleDescription?: string,
    public roleFlag?: string,
    public roleEffDate?: Moment,
    public roleExpDate?: Moment,
    public resources?: IResource[]
  ) {}
}
