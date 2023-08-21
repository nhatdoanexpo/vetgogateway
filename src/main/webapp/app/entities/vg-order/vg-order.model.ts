import dayjs from 'dayjs/esm';

export interface IVgOrder {
  id: number;
  idCustomer?: number | null;
  orderType?: string | null;
  paid?: boolean | null;
  totalAmount?: number | null;
  createdDate?: dayjs.Dayjs | null;
  idPartner?: number | null;
  attributes?: string | null;
}

export type NewVgOrder = Omit<IVgOrder, 'id'> & { id: null };
