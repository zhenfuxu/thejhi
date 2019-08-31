/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GetewayTestModule } from '../../../test.module';
import { UserxDetailComponent } from 'app/entities/userx/userx-detail.component';
import { Userx } from 'app/shared/model/userx.model';

describe('Component Tests', () => {
  describe('Userx Management Detail Component', () => {
    let comp: UserxDetailComponent;
    let fixture: ComponentFixture<UserxDetailComponent>;
    const route = ({ data: of({ userx: new Userx(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GetewayTestModule],
        declarations: [UserxDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserxDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserxDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userx).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
