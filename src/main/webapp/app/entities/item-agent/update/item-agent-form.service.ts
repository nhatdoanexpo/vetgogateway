import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IItemAgent, NewItemAgent } from '../item-agent.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IItemAgent for edit and NewItemAgentFormGroupInput for create.
 */
type ItemAgentFormGroupInput = IItemAgent | PartialWithRequiredKeyOf<NewItemAgent>;

type ItemAgentFormDefaults = Pick<NewItemAgent, 'id' | 'paid'>;

type ItemAgentFormGroupContent = {
  id: FormControl<IItemAgent['id'] | NewItemAgent['id']>;
  idAgent: FormControl<IItemAgent['idAgent']>;
  idItem: FormControl<IItemAgent['idItem']>;
  price: FormControl<IItemAgent['price']>;
  status: FormControl<IItemAgent['status']>;
  idConfigApp: FormControl<IItemAgent['idConfigApp']>;
  paid: FormControl<IItemAgent['paid']>;
};

export type ItemAgentFormGroup = FormGroup<ItemAgentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ItemAgentFormService {
  createItemAgentFormGroup(itemAgent: ItemAgentFormGroupInput = { id: null }): ItemAgentFormGroup {
    const itemAgentRawValue = {
      ...this.getFormDefaults(),
      ...itemAgent,
    };
    return new FormGroup<ItemAgentFormGroupContent>({
      id: new FormControl(
        { value: itemAgentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idAgent: new FormControl(itemAgentRawValue.idAgent),
      idItem: new FormControl(itemAgentRawValue.idItem),
      price: new FormControl(itemAgentRawValue.price),
      status: new FormControl(itemAgentRawValue.status),
      idConfigApp: new FormControl(itemAgentRawValue.idConfigApp),
      paid: new FormControl(itemAgentRawValue.paid),
    });
  }

  getItemAgent(form: ItemAgentFormGroup): IItemAgent | NewItemAgent {
    return form.getRawValue() as IItemAgent | NewItemAgent;
  }

  resetForm(form: ItemAgentFormGroup, itemAgent: ItemAgentFormGroupInput): void {
    const itemAgentRawValue = { ...this.getFormDefaults(), ...itemAgent };
    form.reset(
      {
        ...itemAgentRawValue,
        id: { value: itemAgentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ItemAgentFormDefaults {
    return {
      id: null,
      paid: false,
    };
  }
}
