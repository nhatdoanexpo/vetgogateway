import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDistributor, NewDistributor } from '../distributor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDistributor for edit and NewDistributorFormGroupInput for create.
 */
type DistributorFormGroupInput = IDistributor | PartialWithRequiredKeyOf<NewDistributor>;

type DistributorFormDefaults = Pick<NewDistributor, 'id'>;

type DistributorFormGroupContent = {
  id: FormControl<IDistributor['id'] | NewDistributor['id']>;
  name: FormControl<IDistributor['name']>;
  code: FormControl<IDistributor['code']>;
  phone: FormControl<IDistributor['phone']>;
  email: FormControl<IDistributor['email']>;
  address: FormControl<IDistributor['address']>;
  idAgent: FormControl<IDistributor['idAgent']>;
};

export type DistributorFormGroup = FormGroup<DistributorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DistributorFormService {
  createDistributorFormGroup(distributor: DistributorFormGroupInput = { id: null }): DistributorFormGroup {
    const distributorRawValue = {
      ...this.getFormDefaults(),
      ...distributor,
    };
    return new FormGroup<DistributorFormGroupContent>({
      id: new FormControl(
        { value: distributorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(distributorRawValue.name),
      code: new FormControl(distributorRawValue.code),
      phone: new FormControl(distributorRawValue.phone),
      email: new FormControl(distributorRawValue.email),
      address: new FormControl(distributorRawValue.address),
      idAgent: new FormControl(distributorRawValue.idAgent),
    });
  }

  getDistributor(form: DistributorFormGroup): IDistributor | NewDistributor {
    return form.getRawValue() as IDistributor | NewDistributor;
  }

  resetForm(form: DistributorFormGroup, distributor: DistributorFormGroupInput): void {
    const distributorRawValue = { ...this.getFormDefaults(), ...distributor };
    form.reset(
      {
        ...distributorRawValue,
        id: { value: distributorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DistributorFormDefaults {
    return {
      id: null,
    };
  }
}
