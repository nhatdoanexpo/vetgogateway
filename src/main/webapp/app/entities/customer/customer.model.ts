import dayjs from 'dayjs/esm';

export interface ICustomer {
  id: number;
  name?: string | null;
  code?: string | null;
  email?: string | null;
  phone?: string | null;
  address?: string | null;
  avatar?: string | null;
  createdDate?: dayjs.Dayjs | null;
  status?: string | null;
  idPartner?: number | null;
}

export type NewCustomer = Omit<ICustomer, 'id'> & { id: null };
