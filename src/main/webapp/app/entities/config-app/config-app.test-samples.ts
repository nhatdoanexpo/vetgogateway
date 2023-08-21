import { IConfigApp, NewConfigApp } from './config-app.model';

export const sampleWithRequiredData: IConfigApp = {
  id: 54991,
};

export const sampleWithPartialData: IConfigApp = {
  id: 20427,
  sheetId: 'dynamic',
  status: 'withdrawal withdrawal',
  idCustomer: 47080,
};

export const sampleWithFullData: IConfigApp = {
  id: 6079,
  firebase: 'Expanded',
  sheetId: 'Bedfordshire National Plastic',
  status: 'Plastic',
  idCustomer: 16407,
};

export const sampleWithNewData: NewConfigApp = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
