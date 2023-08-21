import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IConfigApp } from '../config-app.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../config-app.test-samples';

import { ConfigAppService } from './config-app.service';

const requireRestSample: IConfigApp = {
  ...sampleWithRequiredData,
};

describe('ConfigApp Service', () => {
  let service: ConfigAppService;
  let httpMock: HttpTestingController;
  let expectedResult: IConfigApp | IConfigApp[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConfigAppService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ConfigApp', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const configApp = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(configApp).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ConfigApp', () => {
      const configApp = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(configApp).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ConfigApp', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ConfigApp', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ConfigApp', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addConfigAppToCollectionIfMissing', () => {
      it('should add a ConfigApp to an empty array', () => {
        const configApp: IConfigApp = sampleWithRequiredData;
        expectedResult = service.addConfigAppToCollectionIfMissing([], configApp);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(configApp);
      });

      it('should not add a ConfigApp to an array that contains it', () => {
        const configApp: IConfigApp = sampleWithRequiredData;
        const configAppCollection: IConfigApp[] = [
          {
            ...configApp,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addConfigAppToCollectionIfMissing(configAppCollection, configApp);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ConfigApp to an array that doesn't contain it", () => {
        const configApp: IConfigApp = sampleWithRequiredData;
        const configAppCollection: IConfigApp[] = [sampleWithPartialData];
        expectedResult = service.addConfigAppToCollectionIfMissing(configAppCollection, configApp);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(configApp);
      });

      it('should add only unique ConfigApp to an array', () => {
        const configAppArray: IConfigApp[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const configAppCollection: IConfigApp[] = [sampleWithRequiredData];
        expectedResult = service.addConfigAppToCollectionIfMissing(configAppCollection, ...configAppArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const configApp: IConfigApp = sampleWithRequiredData;
        const configApp2: IConfigApp = sampleWithPartialData;
        expectedResult = service.addConfigAppToCollectionIfMissing([], configApp, configApp2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(configApp);
        expect(expectedResult).toContain(configApp2);
      });

      it('should accept null and undefined values', () => {
        const configApp: IConfigApp = sampleWithRequiredData;
        expectedResult = service.addConfigAppToCollectionIfMissing([], null, configApp, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(configApp);
      });

      it('should return initial array if no ConfigApp is added', () => {
        const configAppCollection: IConfigApp[] = [sampleWithRequiredData];
        expectedResult = service.addConfigAppToCollectionIfMissing(configAppCollection, undefined, null);
        expect(expectedResult).toEqual(configAppCollection);
      });
    });

    describe('compareConfigApp', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareConfigApp(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareConfigApp(entity1, entity2);
        const compareResult2 = service.compareConfigApp(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareConfigApp(entity1, entity2);
        const compareResult2 = service.compareConfigApp(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareConfigApp(entity1, entity2);
        const compareResult2 = service.compareConfigApp(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
