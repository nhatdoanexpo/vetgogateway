import dayjs from 'dayjs/esm';

import { ICustomer, NewCustomer } from './customer.model';

export const sampleWithRequiredData: ICustomer = {
  id: 24379,
};

export const sampleWithPartialData: ICustomer = {
  id: 8794,
  code: '1080p',
  email: 'cTrung_T@yahoo.com',
  avatar: 'analyzer invoice',
  createdDate: dayjs('2023-08-21T07:01'),
  status: 'e-business',
};

export const sampleWithFullData: ICustomer = {
  id: 71618,
  name: 'dynamic calculate',
  code: 'Montana Maine',
  email: 'cNhn_Phan@hotmail.com',
  phone: '020 5633 5334',
  address: 'Facilitator',
  avatar: 'Cross-platform revolutionary Games',
  createdDate: dayjs('2023-08-20T16:32'),
  status: 'unleash program',
  idPartner: 82472,
};

export const sampleWithNewData: NewCustomer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
