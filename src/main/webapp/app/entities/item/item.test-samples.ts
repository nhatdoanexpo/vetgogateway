import { IItem, NewItem } from './item.model';

export const sampleWithRequiredData: IItem = {
  id: 89800,
};

export const sampleWithPartialData: IItem = {
  id: 99112,
  name: 'Borders mobile',
  orgPrice: 59618,
  type: 'index',
  status: 'Checking payment',
};

export const sampleWithFullData: IItem = {
  id: 26180,
  name: 'Garden',
  code: 'Tasty harness',
  idCategory: 35643,
  orgPrice: 12587,
  type: 'HDD Maryland',
  status: 'Soft invoice',
};

export const sampleWithNewData: NewItem = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
