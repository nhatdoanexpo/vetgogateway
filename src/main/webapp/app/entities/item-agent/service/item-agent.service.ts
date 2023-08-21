import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IItemAgent, NewItemAgent } from '../item-agent.model';

export type PartialUpdateItemAgent = Partial<IItemAgent> & Pick<IItemAgent, 'id'>;

export type EntityResponseType = HttpResponse<IItemAgent>;
export type EntityArrayResponseType = HttpResponse<IItemAgent[]>;

@Injectable({ providedIn: 'root' })
export class ItemAgentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/item-agents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(itemAgent: NewItemAgent): Observable<EntityResponseType> {
    return this.http.post<IItemAgent>(this.resourceUrl, itemAgent, { observe: 'response' });
  }

  update(itemAgent: IItemAgent): Observable<EntityResponseType> {
    return this.http.put<IItemAgent>(`${this.resourceUrl}/${this.getItemAgentIdentifier(itemAgent)}`, itemAgent, { observe: 'response' });
  }

  partialUpdate(itemAgent: PartialUpdateItemAgent): Observable<EntityResponseType> {
    return this.http.patch<IItemAgent>(`${this.resourceUrl}/${this.getItemAgentIdentifier(itemAgent)}`, itemAgent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItemAgent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItemAgent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getItemAgentIdentifier(itemAgent: Pick<IItemAgent, 'id'>): number {
    return itemAgent.id;
  }

  compareItemAgent(o1: Pick<IItemAgent, 'id'> | null, o2: Pick<IItemAgent, 'id'> | null): boolean {
    return o1 && o2 ? this.getItemAgentIdentifier(o1) === this.getItemAgentIdentifier(o2) : o1 === o2;
  }

  addItemAgentToCollectionIfMissing<Type extends Pick<IItemAgent, 'id'>>(
    itemAgentCollection: Type[],
    ...itemAgentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const itemAgents: Type[] = itemAgentsToCheck.filter(isPresent);
    if (itemAgents.length > 0) {
      const itemAgentCollectionIdentifiers = itemAgentCollection.map(itemAgentItem => this.getItemAgentIdentifier(itemAgentItem)!);
      const itemAgentsToAdd = itemAgents.filter(itemAgentItem => {
        const itemAgentIdentifier = this.getItemAgentIdentifier(itemAgentItem);
        if (itemAgentCollectionIdentifiers.includes(itemAgentIdentifier)) {
          return false;
        }
        itemAgentCollectionIdentifiers.push(itemAgentIdentifier);
        return true;
      });
      return [...itemAgentsToAdd, ...itemAgentCollection];
    }
    return itemAgentCollection;
  }
}
