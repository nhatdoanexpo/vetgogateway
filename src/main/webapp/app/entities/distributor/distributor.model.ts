export interface IDistributor {
  id: number;
  name?: string | null;
  code?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  idAgent?: number | null;
}

export type NewDistributor = Omit<IDistributor, 'id'> & { id: null };
