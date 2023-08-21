import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../transaction-log.test-samples';

import { TransactionLogFormService } from './transaction-log-form.service';

describe('TransactionLog Form Service', () => {
  let service: TransactionLogFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransactionLogFormService);
  });

  describe('Service methods', () => {
    describe('createTransactionLogFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTransactionLogFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idItemAgent: expect.any(Object),
            price: expect.any(Object),
            createdDate: expect.any(Object),
            note: expect.any(Object),
          })
        );
      });

      it('passing ITransactionLog should create a new form with FormGroup', () => {
        const formGroup = service.createTransactionLogFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idItemAgent: expect.any(Object),
            price: expect.any(Object),
            createdDate: expect.any(Object),
            note: expect.any(Object),
          })
        );
      });
    });

    describe('getTransactionLog', () => {
      it('should return NewTransactionLog for default TransactionLog initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTransactionLogFormGroup(sampleWithNewData);

        const transactionLog = service.getTransactionLog(formGroup) as any;

        expect(transactionLog).toMatchObject(sampleWithNewData);
      });

      it('should return NewTransactionLog for empty TransactionLog initial value', () => {
        const formGroup = service.createTransactionLogFormGroup();

        const transactionLog = service.getTransactionLog(formGroup) as any;

        expect(transactionLog).toMatchObject({});
      });

      it('should return ITransactionLog', () => {
        const formGroup = service.createTransactionLogFormGroup(sampleWithRequiredData);

        const transactionLog = service.getTransactionLog(formGroup) as any;

        expect(transactionLog).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITransactionLog should not enable id FormControl', () => {
        const formGroup = service.createTransactionLogFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTransactionLog should disable id FormControl', () => {
        const formGroup = service.createTransactionLogFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
