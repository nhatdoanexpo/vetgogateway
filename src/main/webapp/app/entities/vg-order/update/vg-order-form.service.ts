import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVgOrder, NewVgOrder } from '../vg-order.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVgOrder for edit and NewVgOrderFormGroupInput for create.
 */
type VgOrderFormGroupInput = IVgOrder | PartialWithRequiredKeyOf<NewVgOrder>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVgOrder | NewVgOrder> = Omit<T, 'createdDate'> & {
  createdDate?: string | null;
};

type VgOrderFormRawValue = FormValueOf<IVgOrder>;

type NewVgOrderFormRawValue = FormValueOf<NewVgOrder>;

type VgOrderFormDefaults = Pick<NewVgOrder, 'id' | 'paid' | 'createdDate'>;

type VgOrderFormGroupContent = {
  id: FormControl<VgOrderFormRawValue['id'] | NewVgOrder['id']>;
  idCustomer: FormControl<VgOrderFormRawValue['idCustomer']>;
  orderType: FormControl<VgOrderFormRawValue['orderType']>;
  paid: FormControl<VgOrderFormRawValue['paid']>;
  totalAmount: FormControl<VgOrderFormRawValue['totalAmount']>;
  createdDate: FormControl<VgOrderFormRawValue['createdDate']>;
  idPartner: FormControl<VgOrderFormRawValue['idPartner']>;
  attributes: FormControl<VgOrderFormRawValue['attributes']>;
};

export type VgOrderFormGroup = FormGroup<VgOrderFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VgOrderFormService {
  createVgOrderFormGroup(vgOrder: VgOrderFormGroupInput = { id: null }): VgOrderFormGroup {
    const vgOrderRawValue = this.convertVgOrderToVgOrderRawValue({
      ...this.getFormDefaults(),
      ...vgOrder,
    });
    return new FormGroup<VgOrderFormGroupContent>({
      id: new FormControl(
        { value: vgOrderRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idCustomer: new FormControl(vgOrderRawValue.idCustomer),
      orderType: new FormControl(vgOrderRawValue.orderType),
      paid: new FormControl(vgOrderRawValue.paid),
      totalAmount: new FormControl(vgOrderRawValue.totalAmount),
      createdDate: new FormControl(vgOrderRawValue.createdDate),
      idPartner: new FormControl(vgOrderRawValue.idPartner),
      attributes: new FormControl(vgOrderRawValue.attributes),
    });
  }

  getVgOrder(form: VgOrderFormGroup): IVgOrder | NewVgOrder {
    return this.convertVgOrderRawValueToVgOrder(form.getRawValue() as VgOrderFormRawValue | NewVgOrderFormRawValue);
  }

  resetForm(form: VgOrderFormGroup, vgOrder: VgOrderFormGroupInput): void {
    const vgOrderRawValue = this.convertVgOrderToVgOrderRawValue({ ...this.getFormDefaults(), ...vgOrder });
    form.reset(
      {
        ...vgOrderRawValue,
        id: { value: vgOrderRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): VgOrderFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      paid: false,
      createdDate: currentTime,
    };
  }

  private convertVgOrderRawValueToVgOrder(rawVgOrder: VgOrderFormRawValue | NewVgOrderFormRawValue): IVgOrder | NewVgOrder {
    return {
      ...rawVgOrder,
      createdDate: dayjs(rawVgOrder.createdDate, DATE_TIME_FORMAT),
    };
  }

  private convertVgOrderToVgOrderRawValue(
    vgOrder: IVgOrder | (Partial<NewVgOrder> & VgOrderFormDefaults)
  ): VgOrderFormRawValue | PartialWithRequiredKeyOf<NewVgOrderFormRawValue> {
    return {
      ...vgOrder,
      createdDate: vgOrder.createdDate ? vgOrder.createdDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
