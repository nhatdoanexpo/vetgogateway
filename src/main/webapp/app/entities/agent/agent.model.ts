export interface IAgent {
  id: number;
  code?: string | null;
  name?: string | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
}

export type NewAgent = Omit<IAgent, 'id'> & { id: null };
