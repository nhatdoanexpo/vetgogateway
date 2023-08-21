import { IOrderItem, NewOrderItem } from './order-item.model';

export const sampleWithRequiredData: IOrderItem = {
  id: 62496,
};

export const sampleWithPartialData: IOrderItem = {
  id: 54419,
  idItem: 13429,
  itemName: 'Texas Wooden',
  qty: 41309,
  idVgOrder: 4992,
  discount: 25439,
  idItemAgent: 87914,
};

export const sampleWithFullData: IOrderItem = {
  id: 77444,
  idItem: 96619,
  itemName: 'Chair',
  price: 75328,
  qty: 74744,
  totalPrice: 39748,
  idVgOrder: 29017,
  discount: 19312,
  idItemAgent: 92969,
};

export const sampleWithNewData: NewOrderItem = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
