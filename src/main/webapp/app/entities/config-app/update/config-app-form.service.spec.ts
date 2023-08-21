import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../config-app.test-samples';

import { ConfigAppFormService } from './config-app-form.service';

describe('ConfigApp Form Service', () => {
  let service: ConfigAppFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfigAppFormService);
  });

  describe('Service methods', () => {
    describe('createConfigAppFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createConfigAppFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firebase: expect.any(Object),
            sheetId: expect.any(Object),
            status: expect.any(Object),
            idCustomer: expect.any(Object),
          })
        );
      });

      it('passing IConfigApp should create a new form with FormGroup', () => {
        const formGroup = service.createConfigAppFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firebase: expect.any(Object),
            sheetId: expect.any(Object),
            status: expect.any(Object),
            idCustomer: expect.any(Object),
          })
        );
      });
    });

    describe('getConfigApp', () => {
      it('should return NewConfigApp for default ConfigApp initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createConfigAppFormGroup(sampleWithNewData);

        const configApp = service.getConfigApp(formGroup) as any;

        expect(configApp).toMatchObject(sampleWithNewData);
      });

      it('should return NewConfigApp for empty ConfigApp initial value', () => {
        const formGroup = service.createConfigAppFormGroup();

        const configApp = service.getConfigApp(formGroup) as any;

        expect(configApp).toMatchObject({});
      });

      it('should return IConfigApp', () => {
        const formGroup = service.createConfigAppFormGroup(sampleWithRequiredData);

        const configApp = service.getConfigApp(formGroup) as any;

        expect(configApp).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IConfigApp should not enable id FormControl', () => {
        const formGroup = service.createConfigAppFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewConfigApp should disable id FormControl', () => {
        const formGroup = service.createConfigAppFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
