import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategoryAgent, NewCategoryAgent } from '../category-agent.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategoryAgent for edit and NewCategoryAgentFormGroupInput for create.
 */
type CategoryAgentFormGroupInput = ICategoryAgent | PartialWithRequiredKeyOf<NewCategoryAgent>;

type CategoryAgentFormDefaults = Pick<NewCategoryAgent, 'id'>;

type CategoryAgentFormGroupContent = {
  id: FormControl<ICategoryAgent['id'] | NewCategoryAgent['id']>;
  idAgent: FormControl<ICategoryAgent['idAgent']>;
  idCategory: FormControl<ICategoryAgent['idCategory']>;
};

export type CategoryAgentFormGroup = FormGroup<CategoryAgentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoryAgentFormService {
  createCategoryAgentFormGroup(categoryAgent: CategoryAgentFormGroupInput = { id: null }): CategoryAgentFormGroup {
    const categoryAgentRawValue = {
      ...this.getFormDefaults(),
      ...categoryAgent,
    };
    return new FormGroup<CategoryAgentFormGroupContent>({
      id: new FormControl(
        { value: categoryAgentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idAgent: new FormControl(categoryAgentRawValue.idAgent),
      idCategory: new FormControl(categoryAgentRawValue.idCategory),
    });
  }

  getCategoryAgent(form: CategoryAgentFormGroup): ICategoryAgent | NewCategoryAgent {
    return form.getRawValue() as ICategoryAgent | NewCategoryAgent;
  }

  resetForm(form: CategoryAgentFormGroup, categoryAgent: CategoryAgentFormGroupInput): void {
    const categoryAgentRawValue = { ...this.getFormDefaults(), ...categoryAgent };
    form.reset(
      {
        ...categoryAgentRawValue,
        id: { value: categoryAgentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategoryAgentFormDefaults {
    return {
      id: null,
    };
  }
}
