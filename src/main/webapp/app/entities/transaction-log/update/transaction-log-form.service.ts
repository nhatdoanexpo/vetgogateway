import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITransactionLog, NewTransactionLog } from '../transaction-log.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITransactionLog for edit and NewTransactionLogFormGroupInput for create.
 */
type TransactionLogFormGroupInput = ITransactionLog | PartialWithRequiredKeyOf<NewTransactionLog>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITransactionLog | NewTransactionLog> = Omit<T, 'createdDate'> & {
  createdDate?: string | null;
};

type TransactionLogFormRawValue = FormValueOf<ITransactionLog>;

type NewTransactionLogFormRawValue = FormValueOf<NewTransactionLog>;

type TransactionLogFormDefaults = Pick<NewTransactionLog, 'id' | 'createdDate'>;

type TransactionLogFormGroupContent = {
  id: FormControl<TransactionLogFormRawValue['id'] | NewTransactionLog['id']>;
  idItemAgent: FormControl<TransactionLogFormRawValue['idItemAgent']>;
  price: FormControl<TransactionLogFormRawValue['price']>;
  createdDate: FormControl<TransactionLogFormRawValue['createdDate']>;
  note: FormControl<TransactionLogFormRawValue['note']>;
};

export type TransactionLogFormGroup = FormGroup<TransactionLogFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TransactionLogFormService {
  createTransactionLogFormGroup(transactionLog: TransactionLogFormGroupInput = { id: null }): TransactionLogFormGroup {
    const transactionLogRawValue = this.convertTransactionLogToTransactionLogRawValue({
      ...this.getFormDefaults(),
      ...transactionLog,
    });
    return new FormGroup<TransactionLogFormGroupContent>({
      id: new FormControl(
        { value: transactionLogRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idItemAgent: new FormControl(transactionLogRawValue.idItemAgent),
      price: new FormControl(transactionLogRawValue.price),
      createdDate: new FormControl(transactionLogRawValue.createdDate),
      note: new FormControl(transactionLogRawValue.note),
    });
  }

  getTransactionLog(form: TransactionLogFormGroup): ITransactionLog | NewTransactionLog {
    return this.convertTransactionLogRawValueToTransactionLog(
      form.getRawValue() as TransactionLogFormRawValue | NewTransactionLogFormRawValue
    );
  }

  resetForm(form: TransactionLogFormGroup, transactionLog: TransactionLogFormGroupInput): void {
    const transactionLogRawValue = this.convertTransactionLogToTransactionLogRawValue({ ...this.getFormDefaults(), ...transactionLog });
    form.reset(
      {
        ...transactionLogRawValue,
        id: { value: transactionLogRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TransactionLogFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
    };
  }

  private convertTransactionLogRawValueToTransactionLog(
    rawTransactionLog: TransactionLogFormRawValue | NewTransactionLogFormRawValue
  ): ITransactionLog | NewTransactionLog {
    return {
      ...rawTransactionLog,
      createdDate: dayjs(rawTransactionLog.createdDate, DATE_TIME_FORMAT),
    };
  }

  private convertTransactionLogToTransactionLogRawValue(
    transactionLog: ITransactionLog | (Partial<NewTransactionLog> & TransactionLogFormDefaults)
  ): TransactionLogFormRawValue | PartialWithRequiredKeyOf<NewTransactionLogFormRawValue> {
    return {
      ...transactionLog,
      createdDate: transactionLog.createdDate ? transactionLog.createdDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
