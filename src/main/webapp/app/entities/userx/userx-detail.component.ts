import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserx } from 'app/shared/model/userx.model';

@Component({
  selector: 'jhi-userx-detail',
  templateUrl: './userx-detail.component.html'
})
export class UserxDetailComponent implements OnInit {
  userx: IUserx;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userx }) => {
      this.userx = userx;
    });
  }

  previousState() {
    window.history.back();
  }
}
