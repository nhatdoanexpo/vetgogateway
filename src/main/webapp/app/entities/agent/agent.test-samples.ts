import { IAgent, NewAgent } from './agent.model';

export const sampleWithRequiredData: IAgent = {
  id: 33038,
};

export const sampleWithPartialData: IAgent = {
  id: 44596,
  code: 'Investor',
  email: 'ThngMinh_L82@gmail.com',
  address: 'Branch groupware index',
};

export const sampleWithFullData: IAgent = {
  id: 13150,
  code: 'Nebraska transmit Kansas',
  name: 'Electronics override Highway',
  phone: '027 8647 2240',
  email: 'Thnhn18@yahoo.com',
  address: 'violet Heights Michigan',
};

export const sampleWithNewData: NewAgent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
