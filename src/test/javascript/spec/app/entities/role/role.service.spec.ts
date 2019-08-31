/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RoleService } from 'app/entities/role/role.service';
import { IRole, Role } from 'app/shared/model/role.model';

describe('Service Tests', () => {
  describe('Role Service', () => {
    let injector: TestBed;
    let service: RoleService;
    let httpMock: HttpTestingController;
    let elemDefault: IRole;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(RoleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Role(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            roleEffDate: currentDate.format(DATE_TIME_FORMAT),
            roleExpDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Role', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            roleEffDate: currentDate.format(DATE_TIME_FORMAT),
            roleExpDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            roleEffDate: currentDate,
            roleExpDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Role(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Role', async () => {
        const returnedFromService = Object.assign(
          {
            roleName: 'BBBBBB',
            roleDescription: 'BBBBBB',
            roleFlag: 'BBBBBB',
            roleEffDate: currentDate.format(DATE_TIME_FORMAT),
            roleExpDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            roleEffDate: currentDate,
            roleExpDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Role', async () => {
        const returnedFromService = Object.assign(
          {
            roleName: 'BBBBBB',
            roleDescription: 'BBBBBB',
            roleFlag: 'BBBBBB',
            roleEffDate: currentDate.format(DATE_TIME_FORMAT),
            roleExpDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            roleEffDate: currentDate,
            roleExpDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Role', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});