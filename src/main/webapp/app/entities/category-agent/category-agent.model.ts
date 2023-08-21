export interface ICategoryAgent {
  id: number;
  idAgent?: number | null;
  idCategory?: number | null;
}

export type NewCategoryAgent = Omit<ICategoryAgent, 'id'> & { id: null };
