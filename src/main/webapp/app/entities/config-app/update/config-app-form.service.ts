import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IConfigApp, NewConfigApp } from '../config-app.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IConfigApp for edit and NewConfigAppFormGroupInput for create.
 */
type ConfigAppFormGroupInput = IConfigApp | PartialWithRequiredKeyOf<NewConfigApp>;

type ConfigAppFormDefaults = Pick<NewConfigApp, 'id'>;

type ConfigAppFormGroupContent = {
  id: FormControl<IConfigApp['id'] | NewConfigApp['id']>;
  firebase: FormControl<IConfigApp['firebase']>;
  sheetId: FormControl<IConfigApp['sheetId']>;
  status: FormControl<IConfigApp['status']>;
  idCustomer: FormControl<IConfigApp['idCustomer']>;
};

export type ConfigAppFormGroup = FormGroup<ConfigAppFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ConfigAppFormService {
  createConfigAppFormGroup(configApp: ConfigAppFormGroupInput = { id: null }): ConfigAppFormGroup {
    const configAppRawValue = {
      ...this.getFormDefaults(),
      ...configApp,
    };
    return new FormGroup<ConfigAppFormGroupContent>({
      id: new FormControl(
        { value: configAppRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      firebase: new FormControl(configAppRawValue.firebase),
      sheetId: new FormControl(configAppRawValue.sheetId),
      status: new FormControl(configAppRawValue.status),
      idCustomer: new FormControl(configAppRawValue.idCustomer),
    });
  }

  getConfigApp(form: ConfigAppFormGroup): IConfigApp | NewConfigApp {
    return form.getRawValue() as IConfigApp | NewConfigApp;
  }

  resetForm(form: ConfigAppFormGroup, configApp: ConfigAppFormGroupInput): void {
    const configAppRawValue = { ...this.getFormDefaults(), ...configApp };
    form.reset(
      {
        ...configAppRawValue,
        id: { value: configAppRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ConfigAppFormDefaults {
    return {
      id: null,
    };
  }
}
