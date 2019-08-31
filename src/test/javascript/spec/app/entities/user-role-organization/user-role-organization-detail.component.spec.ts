/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GetewayTestModule } from '../../../test.module';
import { UserRoleOrganizationDetailComponent } from 'app/entities/user-role-organization/user-role-organization-detail.component';
import { UserRoleOrganization } from 'app/shared/model/user-role-organization.model';

describe('Component Tests', () => {
  describe('UserRoleOrganization Management Detail Component', () => {
    let comp: UserRoleOrganizationDetailComponent;
    let fixture: ComponentFixture<UserRoleOrganizationDetailComponent>;
    const route = ({ data: of({ userRoleOrganization: new UserRoleOrganization(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetewayTestModule],
        declarations: [UserRoleOrganizationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserRoleOrganizationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserRoleOrganizationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userRoleOrganization).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
