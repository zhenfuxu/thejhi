/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GetewayTestModule } from '../../../test.module';
import { UserxDeleteDialogComponent } from 'app/entities/userx/userx-delete-dialog.component';
import { UserxService } from 'app/entities/userx/userx.service';

describe('Component Tests', () => {
  describe('Userx Management Delete Component', () => {
    let comp: UserxDeleteDialogComponent;
    let fixture: ComponentFixture<UserxDeleteDialogComponent>;
    let service: UserxService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetewayTestModule],
        declarations: [UserxDeleteDialogComponent]
      })
        .overrideTemplate(UserxDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserxDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserxService);
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
