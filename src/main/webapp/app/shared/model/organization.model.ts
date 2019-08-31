export interface IOrganization {
  id?: number;
  orgName?: string;
  orgCode?: string;
  orgFlag?: string;
  orgAreaCode?: string;
  orgAreaName?: string;
  orgDescription?: string;
  orgLft?: number;
  orgRgt?: number;
  orgLevel?: number;
  orgOrder?: number;
  leaf?: boolean;
  upperId?: number;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public orgName?: string,
    public orgCode?: string,
    public orgFlag?: string,
    public orgAreaCode?: string,
    public orgAreaName?: string,
    public orgDescription?: string,
    public orgLft?: number,
    public orgRgt?: number,
    public orgLevel?: number,
    public orgOrder?: number,
    public leaf?: boolean,
    public upperId?: number
  ) {
    this.leaf = this.leaf || false;
  }
}
