import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVgOrder } from '../vg-order.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../vg-order.test-samples';

import { VgOrderService, RestVgOrder } from './vg-order.service';

const requireRestSample: RestVgOrder = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
};

describe('VgOrder Service', () => {
  let service: VgOrderService;
  let httpMock: HttpTestingController;
  let expectedResult: IVgOrder | IVgOrder[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VgOrderService);
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

    it('should create a VgOrder', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const vgOrder = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(vgOrder).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VgOrder', () => {
      const vgOrder = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(vgOrder).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VgOrder', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VgOrder', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a VgOrder', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVgOrderToCollectionIfMissing', () => {
      it('should add a VgOrder to an empty array', () => {
        const vgOrder: IVgOrder = sampleWithRequiredData;
        expectedResult = service.addVgOrderToCollectionIfMissing([], vgOrder);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vgOrder);
      });

      it('should not add a VgOrder to an array that contains it', () => {
        const vgOrder: IVgOrder = sampleWithRequiredData;
        const vgOrderCollection: IVgOrder[] = [
          {
            ...vgOrder,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVgOrderToCollectionIfMissing(vgOrderCollection, vgOrder);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VgOrder to an array that doesn't contain it", () => {
        const vgOrder: IVgOrder = sampleWithRequiredData;
        const vgOrderCollection: IVgOrder[] = [sampleWithPartialData];
        expectedResult = service.addVgOrderToCollectionIfMissing(vgOrderCollection, vgOrder);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vgOrder);
      });

      it('should add only unique VgOrder to an array', () => {
        const vgOrderArray: IVgOrder[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const vgOrderCollection: IVgOrder[] = [sampleWithRequiredData];
        expectedResult = service.addVgOrderToCollectionIfMissing(vgOrderCollection, ...vgOrderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const vgOrder: IVgOrder = sampleWithRequiredData;
        const vgOrder2: IVgOrder = sampleWithPartialData;
        expectedResult = service.addVgOrderToCollectionIfMissing([], vgOrder, vgOrder2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vgOrder);
        expect(expectedResult).toContain(vgOrder2);
      });

      it('should accept null and undefined values', () => {
        const vgOrder: IVgOrder = sampleWithRequiredData;
        expectedResult = service.addVgOrderToCollectionIfMissing([], null, vgOrder, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vgOrder);
      });

      it('should return initial array if no VgOrder is added', () => {
        const vgOrderCollection: IVgOrder[] = [sampleWithRequiredData];
        expectedResult = service.addVgOrderToCollectionIfMissing(vgOrderCollection, undefined, null);
        expect(expectedResult).toEqual(vgOrderCollection);
      });
    });

    describe('compareVgOrder', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVgOrder(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVgOrder(entity1, entity2);
        const compareResult2 = service.compareVgOrder(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVgOrder(entity1, entity2);
        const compareResult2 = service.compareVgOrder(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVgOrder(entity1, entity2);
        const compareResult2 = service.compareVgOrder(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
