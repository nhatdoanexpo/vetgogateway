import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategoryAgent } from '../category-agent.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../category-agent.test-samples';

import { CategoryAgentService } from './category-agent.service';

const requireRestSample: ICategoryAgent = {
  ...sampleWithRequiredData,
};

describe('CategoryAgent Service', () => {
  let service: CategoryAgentService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategoryAgent | ICategoryAgent[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategoryAgentService);
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

    it('should create a CategoryAgent', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const categoryAgent = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categoryAgent).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategoryAgent', () => {
      const categoryAgent = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categoryAgent).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategoryAgent', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategoryAgent', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategoryAgent', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategoryAgentToCollectionIfMissing', () => {
      it('should add a CategoryAgent to an empty array', () => {
        const categoryAgent: ICategoryAgent = sampleWithRequiredData;
        expectedResult = service.addCategoryAgentToCollectionIfMissing([], categoryAgent);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoryAgent);
      });

      it('should not add a CategoryAgent to an array that contains it', () => {
        const categoryAgent: ICategoryAgent = sampleWithRequiredData;
        const categoryAgentCollection: ICategoryAgent[] = [
          {
            ...categoryAgent,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategoryAgentToCollectionIfMissing(categoryAgentCollection, categoryAgent);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategoryAgent to an array that doesn't contain it", () => {
        const categoryAgent: ICategoryAgent = sampleWithRequiredData;
        const categoryAgentCollection: ICategoryAgent[] = [sampleWithPartialData];
        expectedResult = service.addCategoryAgentToCollectionIfMissing(categoryAgentCollection, categoryAgent);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoryAgent);
      });

      it('should add only unique CategoryAgent to an array', () => {
        const categoryAgentArray: ICategoryAgent[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categoryAgentCollection: ICategoryAgent[] = [sampleWithRequiredData];
        expectedResult = service.addCategoryAgentToCollectionIfMissing(categoryAgentCollection, ...categoryAgentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categoryAgent: ICategoryAgent = sampleWithRequiredData;
        const categoryAgent2: ICategoryAgent = sampleWithPartialData;
        expectedResult = service.addCategoryAgentToCollectionIfMissing([], categoryAgent, categoryAgent2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoryAgent);
        expect(expectedResult).toContain(categoryAgent2);
      });

      it('should accept null and undefined values', () => {
        const categoryAgent: ICategoryAgent = sampleWithRequiredData;
        expectedResult = service.addCategoryAgentToCollectionIfMissing([], null, categoryAgent, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoryAgent);
      });

      it('should return initial array if no CategoryAgent is added', () => {
        const categoryAgentCollection: ICategoryAgent[] = [sampleWithRequiredData];
        expectedResult = service.addCategoryAgentToCollectionIfMissing(categoryAgentCollection, undefined, null);
        expect(expectedResult).toEqual(categoryAgentCollection);
      });
    });

    describe('compareCategoryAgent', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategoryAgent(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategoryAgent(entity1, entity2);
        const compareResult2 = service.compareCategoryAgent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategoryAgent(entity1, entity2);
        const compareResult2 = service.compareCategoryAgent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategoryAgent(entity1, entity2);
        const compareResult2 = service.compareCategoryAgent(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
