export interface IItemAgent {
  id: number;
  idAgent?: number | null;
  idItem?: number | null;
  price?: number | null;
  status?: string | null;
  idConfigApp?: number | null;
  paid?: boolean | null;
}

export type NewItemAgent = Omit<IItemAgent, 'id'> & { id: null };
