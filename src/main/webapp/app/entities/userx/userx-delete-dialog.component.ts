import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserx } from 'app/shared/model/userx.model';
import { UserxService } from './userx.service';

@Component({
  selector: 'jhi-userx-delete-dialog',
  templateUrl: './userx-delete-dialog.component.html'
})
export class UserxDeleteDialogComponent {
  userx: IUserx;

  constructor(protected userxService: UserxService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userxService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userxListModification',
        content: 'Deleted an userx'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-userx-delete-popup',
  template: ''
})
export class UserxDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userx }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserxDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userx = userx;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/userx', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/userx', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
