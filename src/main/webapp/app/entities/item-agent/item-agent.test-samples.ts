import { IItemAgent, NewItemAgent } from './item-agent.model';

export const sampleWithRequiredData: IItemAgent = {
  id: 56885,
};

export const sampleWithPartialData: IItemAgent = {
  id: 98908,
  price: 27589,
  paid: true,
};

export const sampleWithFullData: IItemAgent = {
  id: 78367,
  idAgent: 61656,
  idItem: 46354,
  price: 46192,
  status: 'Usability Handcrafted Global',
  idConfigApp: 95169,
  paid: true,
};

export const sampleWithNewData: NewItemAgent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
