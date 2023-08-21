import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../vg-order.test-samples';

import { VgOrderFormService } from './vg-order-form.service';

describe('VgOrder Form Service', () => {
  let service: VgOrderFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VgOrderFormService);
  });

  describe('Service methods', () => {
    describe('createVgOrderFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVgOrderFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCustomer: expect.any(Object),
            orderType: expect.any(Object),
            paid: expect.any(Object),
            totalAmount: expect.any(Object),
            createdDate: expect.any(Object),
            idPartner: expect.any(Object),
            attributes: expect.any(Object),
          })
        );
      });

      it('passing IVgOrder should create a new form with FormGroup', () => {
        const formGroup = service.createVgOrderFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCustomer: expect.any(Object),
            orderType: expect.any(Object),
            paid: expect.any(Object),
            totalAmount: expect.any(Object),
            createdDate: expect.any(Object),
            idPartner: expect.any(Object),
            attributes: expect.any(Object),
          })
        );
      });
    });

    describe('getVgOrder', () => {
      it('should return NewVgOrder for default VgOrder initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createVgOrderFormGroup(sampleWithNewData);

        const vgOrder = service.getVgOrder(formGroup) as any;

        expect(vgOrder).toMatchObject(sampleWithNewData);
      });

      it('should return NewVgOrder for empty VgOrder initial value', () => {
        const formGroup = service.createVgOrderFormGroup();

        const vgOrder = service.getVgOrder(formGroup) as any;

        expect(vgOrder).toMatchObject({});
      });

      it('should return IVgOrder', () => {
        const formGroup = service.createVgOrderFormGroup(sampleWithRequiredData);

        const vgOrder = service.getVgOrder(formGroup) as any;

        expect(vgOrder).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVgOrder should not enable id FormControl', () => {
        const formGroup = service.createVgOrderFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVgOrder should disable id FormControl', () => {
        const formGroup = service.createVgOrderFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
