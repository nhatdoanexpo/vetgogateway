export interface IOrderItem {
  id: number;
  idItem?: number | null;
  itemName?: string | null;
  price?: number | null;
  qty?: number | null;
  totalPrice?: number | null;
  idVgOrder?: number | null;
  discount?: number | null;
  idItemAgent?: number | null;
}

export type NewOrderItem = Omit<IOrderItem, 'id'> & { id: null };
