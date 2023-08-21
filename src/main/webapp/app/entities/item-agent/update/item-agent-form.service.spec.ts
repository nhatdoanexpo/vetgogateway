import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../item-agent.test-samples';

import { ItemAgentFormService } from './item-agent-form.service';

describe('ItemAgent Form Service', () => {
  let service: ItemAgentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemAgentFormService);
  });

  describe('Service methods', () => {
    describe('createItemAgentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createItemAgentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idAgent: expect.any(Object),
            idItem: expect.any(Object),
            price: expect.any(Object),
            status: expect.any(Object),
            idConfigApp: expect.any(Object),
            paid: expect.any(Object),
          })
        );
      });

      it('passing IItemAgent should create a new form with FormGroup', () => {
        const formGroup = service.createItemAgentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idAgent: expect.any(Object),
            idItem: expect.any(Object),
            price: expect.any(Object),
            status: expect.any(Object),
            idConfigApp: expect.any(Object),
            paid: expect.any(Object),
          })
        );
      });
    });

    describe('getItemAgent', () => {
      it('should return NewItemAgent for default ItemAgent initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createItemAgentFormGroup(sampleWithNewData);

        const itemAgent = service.getItemAgent(formGroup) as any;

        expect(itemAgent).toMatchObject(sampleWithNewData);
      });

      it('should return NewItemAgent for empty ItemAgent initial value', () => {
        const formGroup = service.createItemAgentFormGroup();

        const itemAgent = service.getItemAgent(formGroup) as any;

        expect(itemAgent).toMatchObject({});
      });

      it('should return IItemAgent', () => {
        const formGroup = service.createItemAgentFormGroup(sampleWithRequiredData);

        const itemAgent = service.getItemAgent(formGroup) as any;

        expect(itemAgent).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IItemAgent should not enable id FormControl', () => {
        const formGroup = service.createItemAgentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewItemAgent should disable id FormControl', () => {
        const formGroup = service.createItemAgentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
