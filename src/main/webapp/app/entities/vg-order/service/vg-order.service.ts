import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVgOrder, NewVgOrder } from '../vg-order.model';

export type PartialUpdateVgOrder = Partial<IVgOrder> & Pick<IVgOrder, 'id'>;

type RestOf<T extends IVgOrder | NewVgOrder> = Omit<T, 'createdDate'> & {
  createdDate?: string | null;
};

export type RestVgOrder = RestOf<IVgOrder>;

export type NewRestVgOrder = RestOf<NewVgOrder>;

export type PartialUpdateRestVgOrder = RestOf<PartialUpdateVgOrder>;

export type EntityResponseType = HttpResponse<IVgOrder>;
export type EntityArrayResponseType = HttpResponse<IVgOrder[]>;

@Injectable({ providedIn: 'root' })
export class VgOrderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vg-orders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vgOrder: NewVgOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vgOrder);
    return this.http
      .post<RestVgOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(vgOrder: IVgOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vgOrder);
    return this.http
      .put<RestVgOrder>(`${this.resourceUrl}/${this.getVgOrderIdentifier(vgOrder)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(vgOrder: PartialUpdateVgOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vgOrder);
    return this.http
      .patch<RestVgOrder>(`${this.resourceUrl}/${this.getVgOrderIdentifier(vgOrder)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVgOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVgOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVgOrderIdentifier(vgOrder: Pick<IVgOrder, 'id'>): number {
    return vgOrder.id;
  }

  compareVgOrder(o1: Pick<IVgOrder, 'id'> | null, o2: Pick<IVgOrder, 'id'> | null): boolean {
    return o1 && o2 ? this.getVgOrderIdentifier(o1) === this.getVgOrderIdentifier(o2) : o1 === o2;
  }

  addVgOrderToCollectionIfMissing<Type extends Pick<IVgOrder, 'id'>>(
    vgOrderCollection: Type[],
    ...vgOrdersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const vgOrders: Type[] = vgOrdersToCheck.filter(isPresent);
    if (vgOrders.length > 0) {
      const vgOrderCollectionIdentifiers = vgOrderCollection.map(vgOrderItem => this.getVgOrderIdentifier(vgOrderItem)!);
      const vgOrdersToAdd = vgOrders.filter(vgOrderItem => {
        const vgOrderIdentifier = this.getVgOrderIdentifier(vgOrderItem);
        if (vgOrderCollectionIdentifiers.includes(vgOrderIdentifier)) {
          return false;
        }
        vgOrderCollectionIdentifiers.push(vgOrderIdentifier);
        return true;
      });
      return [...vgOrdersToAdd, ...vgOrderCollection];
    }
    return vgOrderCollection;
  }

  protected convertDateFromClient<T extends IVgOrder | NewVgOrder | PartialUpdateVgOrder>(vgOrder: T): RestOf<T> {
    return {
      ...vgOrder,
      createdDate: vgOrder.createdDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVgOrder: RestVgOrder): IVgOrder {
    return {
      ...restVgOrder,
      createdDate: restVgOrder.createdDate ? dayjs(restVgOrder.createdDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVgOrder>): HttpResponse<IVgOrder> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVgOrder[]>): HttpResponse<IVgOrder[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
