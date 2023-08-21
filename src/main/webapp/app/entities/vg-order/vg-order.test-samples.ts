import dayjs from 'dayjs/esm';

import { IVgOrder, NewVgOrder } from './vg-order.model';

export const sampleWithRequiredData: IVgOrder = {
  id: 79648,
};

export const sampleWithPartialData: IVgOrder = {
  id: 98310,
  orderType: 'Riel',
  totalAmount: 10302,
  createdDate: dayjs('2023-08-21T08:26'),
};

export const sampleWithFullData: IVgOrder = {
  id: 61105,
  idCustomer: 58651,
  orderType: 'models Administrator',
  paid: false,
  totalAmount: 99298,
  createdDate: dayjs('2023-08-20T22:56'),
  idPartner: 58046,
  attributes: 'Sports SMTP Multi-lateral',
};

export const sampleWithNewData: NewVgOrder = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
