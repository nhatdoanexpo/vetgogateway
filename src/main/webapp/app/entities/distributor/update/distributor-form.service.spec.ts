import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../distributor.test-samples';

import { DistributorFormService } from './distributor-form.service';

describe('Distributor Form Service', () => {
  let service: DistributorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DistributorFormService);
  });

  describe('Service methods', () => {
    describe('createDistributorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDistributorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            idAgent: expect.any(Object),
          })
        );
      });

      it('passing IDistributor should create a new form with FormGroup', () => {
        const formGroup = service.createDistributorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            idAgent: expect.any(Object),
          })
        );
      });
    });

    describe('getDistributor', () => {
      it('should return NewDistributor for default Distributor initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDistributorFormGroup(sampleWithNewData);

        const distributor = service.getDistributor(formGroup) as any;

        expect(distributor).toMatchObject(sampleWithNewData);
      });

      it('should return NewDistributor for empty Distributor initial value', () => {
        const formGroup = service.createDistributorFormGroup();

        const distributor = service.getDistributor(formGroup) as any;

        expect(distributor).toMatchObject({});
      });

      it('should return IDistributor', () => {
        const formGroup = service.createDistributorFormGroup(sampleWithRequiredData);

        const distributor = service.getDistributor(formGroup) as any;

        expect(distributor).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDistributor should not enable id FormControl', () => {
        const formGroup = service.createDistributorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDistributor should disable id FormControl', () => {
        const formGroup = service.createDistributorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
