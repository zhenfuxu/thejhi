/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GetewayTestModule } from '../../../test.module';
import { UserRoleOrganizationDeleteDialogComponent } from 'app/entities/user-role-organization/user-role-organization-delete-dialog.component';
import { UserRoleOrganizationService } from 'app/entities/user-role-organization/user-role-organization.service';

describe('Component Tests', () => {
  describe('UserRoleOrganization Management Delete Component', () => {
    let comp: UserRoleOrganizationDeleteDialogComponent;
    let fixture: ComponentFixture<UserRoleOrganizationDeleteDialogComponent>;
    let service: UserRoleOrganizationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetewayTestModule],
        declarations: [UserRoleOrganizationDeleteDialogComponent]
      })
        .overrideTemplate(UserRoleOrganizationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserRoleOrganizationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserRoleOrganizationService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
