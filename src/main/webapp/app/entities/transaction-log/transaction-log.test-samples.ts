import dayjs from 'dayjs/esm';

import { ITransactionLog, NewTransactionLog } from './transaction-log.model';

export const sampleWithRequiredData: ITransactionLog = {
  id: 44129,
};

export const sampleWithPartialData: ITransactionLog = {
  id: 4891,
  price: 13453,
  note: 'Turnpike concept',
};

export const sampleWithFullData: ITransactionLog = {
  id: 38939,
  idItemAgent: 10828,
  price: 54238,
  createdDate: dayjs('2023-08-21T05:19'),
  note: 'B2B',
};

export const sampleWithNewData: NewTransactionLog = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
