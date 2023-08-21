import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOrderItem, NewOrderItem } from '../order-item.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrderItem for edit and NewOrderItemFormGroupInput for create.
 */
type OrderItemFormGroupInput = IOrderItem | PartialWithRequiredKeyOf<NewOrderItem>;

type OrderItemFormDefaults = Pick<NewOrderItem, 'id'>;

type OrderItemFormGroupContent = {
  id: FormControl<IOrderItem['id'] | NewOrderItem['id']>;
  idItem: FormControl<IOrderItem['idItem']>;
  itemName: FormControl<IOrderItem['itemName']>;
  price: FormControl<IOrderItem['price']>;
  qty: FormControl<IOrderItem['qty']>;
  totalPrice: FormControl<IOrderItem['totalPrice']>;
  idVgOrder: FormControl<IOrderItem['idVgOrder']>;
  discount: FormControl<IOrderItem['discount']>;
  idItemAgent: FormControl<IOrderItem['idItemAgent']>;
};

export type OrderItemFormGroup = FormGroup<OrderItemFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrderItemFormService {
  createOrderItemFormGroup(orderItem: OrderItemFormGroupInput = { id: null }): OrderItemFormGroup {
    const orderItemRawValue = {
      ...this.getFormDefaults(),
      ...orderItem,
    };
    return new FormGroup<OrderItemFormGroupContent>({
      id: new FormControl(
        { value: orderItemRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idItem: new FormControl(orderItemRawValue.idItem),
      itemName: new FormControl(orderItemRawValue.itemName),
      price: new FormControl(orderItemRawValue.price),
      qty: new FormControl(orderItemRawValue.qty),
      totalPrice: new FormControl(orderItemRawValue.totalPrice),
      idVgOrder: new FormControl(orderItemRawValue.idVgOrder),
      discount: new FormControl(orderItemRawValue.discount),
      idItemAgent: new FormControl(orderItemRawValue.idItemAgent),
    });
  }

  getOrderItem(form: OrderItemFormGroup): IOrderItem | NewOrderItem {
    return form.getRawValue() as IOrderItem | NewOrderItem;
  }

  resetForm(form: OrderItemFormGroup, orderItem: OrderItemFormGroupInput): void {
    const orderItemRawValue = { ...this.getFormDefaults(), ...orderItem };
    form.reset(
      {
        ...orderItemRawValue,
        id: { value: orderItemRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrderItemFormDefaults {
    return {
      id: null,
    };
  }
}
