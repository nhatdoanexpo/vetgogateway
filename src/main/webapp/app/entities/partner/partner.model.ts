export interface IPartner {
  id: number;
  name?: string | null;
  code?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  idDistributor?: number | null;
  status?: string | null;
}

export type NewPartner = Omit<IPartner, 'id'> & { id: null };
