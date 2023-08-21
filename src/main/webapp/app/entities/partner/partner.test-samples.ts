import { IPartner, NewPartner } from './partner.model';

export const sampleWithRequiredData: IPartner = {
  id: 86899,
};

export const sampleWithPartialData: IPartner = {
  id: 45143,
  name: 'Account copying',
  phone: '029 2358 7137',
  idDistributor: 888,
  status: 'Engineer Avon',
};

export const sampleWithFullData: IPartner = {
  id: 1754,
  name: 'Accounts connecting lavender',
  code: 'Chair',
  phone: '0231 1491 7695',
  email: 'BchNgc37@gmail.com',
  address: 'Quality-focused',
  idDistributor: 65938,
  status: 'Chief Ameliorated Auto',
};

export const sampleWithNewData: NewPartner = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
