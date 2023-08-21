import { IDistributor, NewDistributor } from './distributor.model';

export const sampleWithRequiredData: IDistributor = {
  id: 78160,
};

export const sampleWithPartialData: IDistributor = {
  id: 57873,
  name: 'Rubber Account Colorado',
  code: 'e-tailers Rwanda',
  address: 'payment Danish Chair',
};

export const sampleWithFullData: IDistributor = {
  id: 8288,
  name: 'Cambridgeshire Sol Borders',
  code: 'pixel internet',
  phone: '022 2630 0734',
  email: 'HiUyn_Trng@yahoo.com',
  address: 'payment withdrawal',
  idAgent: 19106,
};

export const sampleWithNewData: NewDistributor = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
