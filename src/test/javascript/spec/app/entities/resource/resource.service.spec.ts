/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ResourceService } from 'app/entities/resource/resource.service';
import { IResource, Resource } from 'app/shared/model/resource.model';

describe('Service Tests', () => {
  describe('Resource Service', () => {
    let injector: TestBed;
    let service: ResourceService;
    let httpMock: HttpTestingController;
    let elemDefault: IResource;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ResourceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Resource(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        0,
        0,
        0,
        false,
        false,
        false,
        false,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            resEffDate: currentDate.format(DATE_TIME_FORMAT),
            resExpDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Resource', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            resEffDate: currentDate.format(DATE_TIME_FORMAT),
            resExpDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            resEffDate: currentDate,
            resExpDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Resource(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Resource', async () => {
        const returnedFromService = Object.assign(
          {
            resRouterLink: 'BBBBBB',
            resDescription: 'BBBBBB',
            resFlag: 'BBBBBB',
            resOperate: 'BBBBBB',
            resHref: 'BBBBBB',
            resSrc: 'BBBBBB',
            resText: 'BBBBBB',
            resClass: 'BBBBBB',
            resEffDate: currentDate.format(DATE_TIME_FORMAT),
            resExpDate: currentDate.format(DATE_TIME_FORMAT),
            resLft: 1,
            resRgt: 1,
            resLevel: 1,
            resOrder: 1,
            leaf: true,
            resDisabled: true,
            resChecked: true,
            resExpanded: true,
            resSelected: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resEffDate: currentDate,
            resExpDate: currentDate
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

      it('should return a list of Resource', async () => {
        const returnedFromService = Object.assign(
          {
            resRouterLink: 'BBBBBB',
            resDescription: 'BBBBBB',
            resFlag: 'BBBBBB',
            resOperate: 'BBBBBB',
            resHref: 'BBBBBB',
            resSrc: 'BBBBBB',
            resText: 'BBBBBB',
            resClass: 'BBBBBB',
            resEffDate: currentDate.format(DATE_TIME_FORMAT),
            resExpDate: currentDate.format(DATE_TIME_FORMAT),
            resLft: 1,
            resRgt: 1,
            resLevel: 1,
            resOrder: 1,
            leaf: true,
            resDisabled: true,
            resChecked: true,
            resExpanded: true,
            resSelected: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            resEffDate: currentDate,
            resExpDate: currentDate
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

      it('should delete a Resource', async () => {
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
