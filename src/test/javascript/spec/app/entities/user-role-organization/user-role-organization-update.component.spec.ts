/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GetewayTestModule } from '../../../test.module';
import { UserRoleOrganizationUpdateComponent } from 'app/entities/user-role-organization/user-role-organization-update.component';
import { UserRoleOrganizationService } from 'app/entities/user-role-organization/user-role-organization.service';
import { UserRoleOrganization } from 'app/shared/model/user-role-organization.model';

describe('Component Tests', () => {
  describe('UserRoleOrganization Management Update Component', () => {
    let comp: UserRoleOrganizationUpdateComponent;
    let fixture: ComponentFixture<UserRoleOrganizationUpdateComponent>;
    let service: UserRoleOrganizationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetewayTestModule],
        declarations: [UserRoleOrganizationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserRoleOrganizationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserRoleOrganizationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserRoleOrganizationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserRoleOrganization(123);
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
        const entity = new UserRoleOrganization();
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
