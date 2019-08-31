import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserRoleOrganization } from 'app/shared/model/user-role-organization.model';
import { UserRoleOrganizationService } from './user-role-organization.service';

@Component({
  selector: 'jhi-user-role-organization-delete-dialog',
  templateUrl: './user-role-organization-delete-dialog.component.html'
})
export class UserRoleOrganizationDeleteDialogComponent {
  userRoleOrganization: IUserRoleOrganization;

  constructor(
    protected userRoleOrganizationService: UserRoleOrganizationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userRoleOrganizationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userRoleOrganizationListModification',
        content: 'Deleted an userRoleOrganization'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-role-organization-delete-popup',
  template: ''
})
export class UserRoleOrganizationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userRoleOrganization }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserRoleOrganizationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.userRoleOrganization = userRoleOrganization;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/user-role-organization', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/user-role-organization', { outlets: { popup: null } }]);
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
