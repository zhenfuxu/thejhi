import {Component } from '@angular/core';
import { navItems } from '../../_nav';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent {
  public sidebarMinimized = false;
  public navItems = navItems;

  toggleMinimize(e:any):void {
    this.sidebarMinimized = e;
    // console.log('toggleMinimize', e, this.sidebarMinimized);
  }
}
