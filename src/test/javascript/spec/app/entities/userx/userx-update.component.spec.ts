/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GetewayTestModule } from '../../../test.module';
import { UserxUpdateComponent } from 'app/entities/userx/userx-update.component';
import { UserxService } from 'app/entities/userx/userx.service';
import { Userx } from 'app/shared/model/userx.model';

describe('Component Tests', () => {
  describe('Userx Management Update Component', () => {
    let comp: UserxUpdateComponent;
    let fixture: ComponentFixture<UserxUpdateComponent>;
    let service: UserxService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetewayTestModule],
        declarations: [UserxUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserxUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserxUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserxService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Userx(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Userx();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
