import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IItemAgent } from '../item-agent.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../item-agent.test-samples';

import { ItemAgentService } from './item-agent.service';

const requireRestSample: IItemAgent = {
  ...sampleWithRequiredData,
};

describe('ItemAgent Service', () => {
  let service: ItemAgentService;
  let httpMock: HttpTestingController;
  let expectedResult: IItemAgent | IItemAgent[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ItemAgentService);
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

    it('should create a ItemAgent', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const itemAgent = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(itemAgent).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ItemAgent', () => {
      const itemAgent = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(itemAgent).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ItemAgent', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ItemAgent', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ItemAgent', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addItemAgentToCollectionIfMissing', () => {
      it('should add a ItemAgent to an empty array', () => {
        const itemAgent: IItemAgent = sampleWithRequiredData;
        expectedResult = service.addItemAgentToCollectionIfMissing([], itemAgent);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemAgent);
      });

      it('should not add a ItemAgent to an array that contains it', () => {
        const itemAgent: IItemAgent = sampleWithRequiredData;
        const itemAgentCollection: IItemAgent[] = [
          {
            ...itemAgent,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addItemAgentToCollectionIfMissing(itemAgentCollection, itemAgent);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ItemAgent to an array that doesn't contain it", () => {
        const itemAgent: IItemAgent = sampleWithRequiredData;
        const itemAgentCollection: IItemAgent[] = [sampleWithPartialData];
        expectedResult = service.addItemAgentToCollectionIfMissing(itemAgentCollection, itemAgent);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemAgent);
      });

      it('should add only unique ItemAgent to an array', () => {
        const itemAgentArray: IItemAgent[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const itemAgentCollection: IItemAgent[] = [sampleWithRequiredData];
        expectedResult = service.addItemAgentToCollectionIfMissing(itemAgentCollection, ...itemAgentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const itemAgent: IItemAgent = sampleWithRequiredData;
        const itemAgent2: IItemAgent = sampleWithPartialData;
        expectedResult = service.addItemAgentToCollectionIfMissing([], itemAgent, itemAgent2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemAgent);
        expect(expectedResult).toContain(itemAgent2);
      });

      it('should accept null and undefined values', () => {
        const itemAgent: IItemAgent = sampleWithRequiredData;
        expectedResult = service.addItemAgentToCollectionIfMissing([], null, itemAgent, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemAgent);
      });

      it('should return initial array if no ItemAgent is added', () => {
        const itemAgentCollection: IItemAgent[] = [sampleWithRequiredData];
        expectedResult = service.addItemAgentToCollectionIfMissing(itemAgentCollection, undefined, null);
        expect(expectedResult).toEqual(itemAgentCollection);
      });
    });

    describe('compareItemAgent', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareItemAgent(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareItemAgent(entity1, entity2);
        const compareResult2 = service.compareItemAgent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareItemAgent(entity1, entity2);
        const compareResult2 = service.compareItemAgent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareItemAgent(entity1, entity2);
        const compareResult2 = service.compareItemAgent(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
