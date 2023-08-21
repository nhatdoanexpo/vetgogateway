export interface IConfigApp {
  id: number;
  firebase?: string | null;
  sheetId?: string | null;
  status?: string | null;
  idCustomer?: number | null;
}

export type NewConfigApp = Omit<IConfigApp, 'id'> & { id: null };
