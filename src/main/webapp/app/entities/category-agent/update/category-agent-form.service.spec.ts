import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../category-agent.test-samples';

import { CategoryAgentFormService } from './category-agent-form.service';

describe('CategoryAgent Form Service', () => {
  let service: CategoryAgentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoryAgentFormService);
  });

  describe('Service methods', () => {
    describe('createCategoryAgentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategoryAgentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idAgent: expect.any(Object),
            idCategory: expect.any(Object),
          })
        );
      });

      it('passing ICategoryAgent should create a new form with FormGroup', () => {
        const formGroup = service.createCategoryAgentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idAgent: expect.any(Object),
            idCategory: expect.any(Object),
          })
        );
      });
    });

    describe('getCategoryAgent', () => {
      it('should return NewCategoryAgent for default CategoryAgent initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCategoryAgentFormGroup(sampleWithNewData);

        const categoryAgent = service.getCategoryAgent(formGroup) as any;

        expect(categoryAgent).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategoryAgent for empty CategoryAgent initial value', () => {
        const formGroup = service.createCategoryAgentFormGroup();

        const categoryAgent = service.getCategoryAgent(formGroup) as any;

        expect(categoryAgent).toMatchObject({});
      });

      it('should return ICategoryAgent', () => {
        const formGroup = service.createCategoryAgentFormGroup(sampleWithRequiredData);

        const categoryAgent = service.getCategoryAgent(formGroup) as any;

        expect(categoryAgent).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategoryAgent should not enable id FormControl', () => {
        const formGroup = service.createCategoryAgentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategoryAgent should disable id FormControl', () => {
        const formGroup = service.createCategoryAgentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
