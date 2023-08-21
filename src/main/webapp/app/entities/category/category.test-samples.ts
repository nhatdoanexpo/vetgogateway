import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 2529,
};

export const sampleWithPartialData: ICategory = {
  id: 7305,
  name: 'uniform',
  description: 'Clothing navigate gold',
  id_parent: 'PCI virtual Chair',
};

export const sampleWithFullData: ICategory = {
  id: 83252,
  name: 'Borders bus',
  description: 'Accounts Computer Handmade',
  id_parent: 'generating',
  status: 'pixel syndicate',
};

export const sampleWithNewData: NewCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
