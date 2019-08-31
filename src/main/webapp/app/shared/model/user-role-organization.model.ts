export interface IUserRoleOrganization {
  id?: number;
  userName?: string;
  roleName?: string;
  orgName?: string;
  userLogin?: string;
  userId?: number;
  roleRoleName?: string;
  roleId?: number;
  organizationOrgName?: string;
  organizationId?: number;
}

export class UserRoleOrganization implements IUserRoleOrganization {
  constructor(
    public id?: number,
    public userName?: string,
    public roleName?: string,
    public orgName?: string,
    public userLogin?: string,
    public userId?: number,
    public roleRoleName?: string,
    public roleId?: number,
    public organizationOrgName?: string,
    public organizationId?: number
  ) {}
}
