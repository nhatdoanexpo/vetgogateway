export interface ICategory {
  id: number;
  name?: string | null;
  description?: string | null;
  id_parent?: string | null;
  status?: string | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
