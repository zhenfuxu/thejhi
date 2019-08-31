import { Moment } from 'moment';
import { IRole } from 'app/shared/model/role.model';

export interface IUserx {
  id?: number;
  login?: string;
  password?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  imageUrl?: string;
  activationKey?: string;
  resetKey?: string;
  resetDate?: Moment;
  organizationId?: number;
  roles?: IRole[];
}

export class Userx implements IUserx {
  constructor(
    public id?: number,
    public login?: string,
    public password?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public imageUrl?: string,
    public activationKey?: string,
    public resetKey?: string,
    public resetDate?: Moment,
    public organizationId?: number,
    public roles?: IRole[]
  ) {
    this.activated = this.activated || false;
  }
}
