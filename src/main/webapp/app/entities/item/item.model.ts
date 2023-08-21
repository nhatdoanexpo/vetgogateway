export interface IItem {
  id: number;
  name?: string | null;
  code?: string | null;
  idCategory?: number | null;
  orgPrice?: number | null;
  type?: string | null;
  status?: string | null;
}

export type NewItem = Omit<IItem, 'id'> & { id: null };
