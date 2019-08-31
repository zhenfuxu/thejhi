import { Moment } from 'moment';

export interface IResource {
  id?: number;
  resRouterLink?: string;
  resDescription?: string;
  resFlag?: string;
  resOperate?: string;
  resHref?: string;
  resSrc?: string;
  resText?: string;
  resClass?: string;
  resEffDate?: Moment;
  resExpDate?: Moment;
  resLft?: number;
  resRgt?: number;
  resLevel?: number;
  resOrder?: number;
  leaf?: boolean;
  resDisabled?: boolean;
  resChecked?: boolean;
  resExpanded?: boolean;
  resSelected?: boolean;
  upperId?: number;
}

export class Resource implements IResource {
  constructor(
    public id?: number,
    public resRouterLink?: string,
    public resDescription?: string,
    public resFlag?: string,
    public resOperate?: string,
    public resHref?: string,
    public resSrc?: string,
    public resText?: string,
    public resClass?: string,
    public resEffDate?: Moment,
    public resExpDate?: Moment,
    public resLft?: number,
    public resRgt?: number,
    public resLevel?: number,
    public resOrder?: number,
    public leaf?: boolean,
    public resDisabled?: boolean,
    public resChecked?: boolean,
    public resExpanded?: boolean,
    public resSelected?: boolean,
    public upperId?: number
  ) {
    this.leaf = this.leaf || false;
    this.resDisabled = this.resDisabled || false;
    this.resChecked = this.resChecked || false;
    this.resExpanded = this.resExpanded || false;
    this.resSelected = this.resSelected || false;
  }
}
