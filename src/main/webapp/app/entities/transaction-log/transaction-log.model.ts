import dayjs from 'dayjs/esm';

export interface ITransactionLog {
  id: number;
  idItemAgent?: number | null;
  price?: number | null;
  createdDate?: dayjs.Dayjs | null;
  note?: string | null;
}

export type NewTransactionLog = Omit<ITransactionLog, 'id'> & { id: null };
