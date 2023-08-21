import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDistributor } from '../distributor.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../distributor.test-samples';

import { DistributorService } from './distributor.service';

const requireRestSample: IDistributor = {
  ...sampleWithRequiredData,
};

describe('Distributor Service', () => {
  let service: DistributorService;
  let httpMock: HttpTestingController;
  let expectedResult: IDistributor | IDistributor[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DistributorService);
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

    it('should create a Distributor', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const distributor = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(distributor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Distributor', () => {
      const distributor = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(distributor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Distributor', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Distributor', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Distributor', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDistributorToCollectionIfMissing', () => {
      it('should add a Distributor to an empty array', () => {
        const distributor: IDistributor = sampleWithRequiredData;
        expectedResult = service.addDistributorToCollectionIfMissing([], distributor);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(distributor);
      });

      it('should not add a Distributor to an array that contains it', () => {
        const distributor: IDistributor = sampleWithRequiredData;
        const distributorCollection: IDistributor[] = [
          {
            ...distributor,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDistributorToCollectionIfMissing(distributorCollection, distributor);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Distributor to an array that doesn't contain it", () => {
        const distributor: IDistributor = sampleWithRequiredData;
        const distributorCollection: IDistributor[] = [sampleWithPartialData];
        expectedResult = service.addDistributorToCollectionIfMissing(distributorCollection, distributor);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(distributor);
      });

      it('should add only unique Distributor to an array', () => {
        const distributorArray: IDistributor[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const distributorCollection: IDistributor[] = [sampleWithRequiredData];
        expectedResult = service.addDistributorToCollectionIfMissing(distributorCollection, ...distributorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const distributor: IDistributor = sampleWithRequiredData;
        const distributor2: IDistributor = sampleWithPartialData;
        expectedResult = service.addDistributorToCollectionIfMissing([], distributor, distributor2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(distributor);
        expect(expectedResult).toContain(distributor2);
      });

      it('should accept null and undefined values', () => {
        const distributor: IDistributor = sampleWithRequiredData;
        expectedResult = service.addDistributorToCollectionIfMissing([], null, distributor, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(distributor);
      });

      it('should return initial array if no Distributor is added', () => {
        const distributorCollection: IDistributor[] = [sampleWithRequiredData];
        expectedResult = service.addDistributorToCollectionIfMissing(distributorCollection, undefined, null);
        expect(expectedResult).toEqual(distributorCollection);
      });
    });

    describe('compareDistributor', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDistributor(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDistributor(entity1, entity2);
        const compareResult2 = service.compareDistributor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDistributor(entity1, entity2);
        const compareResult2 = service.compareDistributor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDistributor(entity1, entity2);
        const compareResult2 = service.compareDistributor(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
