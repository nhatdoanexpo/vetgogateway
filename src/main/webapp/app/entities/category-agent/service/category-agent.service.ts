import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoryAgent, NewCategoryAgent } from '../category-agent.model';

export type PartialUpdateCategoryAgent = Partial<ICategoryAgent> & Pick<ICategoryAgent, 'id'>;

export type EntityResponseType = HttpResponse<ICategoryAgent>;
export type EntityArrayResponseType = HttpResponse<ICategoryAgent[]>;

@Injectable({ providedIn: 'root' })
export class CategoryAgentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/category-agents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categoryAgent: NewCategoryAgent): Observable<EntityResponseType> {
    return this.http.post<ICategoryAgent>(this.resourceUrl, categoryAgent, { observe: 'response' });
  }

  update(categoryAgent: ICategoryAgent): Observable<EntityResponseType> {
    return this.http.put<ICategoryAgent>(`${this.resourceUrl}/${this.getCategoryAgentIdentifier(categoryAgent)}`, categoryAgent, {
      observe: 'response',
    });
  }

  partialUpdate(categoryAgent: PartialUpdateCategoryAgent): Observable<EntityResponseType> {
    return this.http.patch<ICategoryAgent>(`${this.resourceUrl}/${this.getCategoryAgentIdentifier(categoryAgent)}`, categoryAgent, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoryAgent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoryAgent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoryAgentIdentifier(categoryAgent: Pick<ICategoryAgent, 'id'>): number {
    return categoryAgent.id;
  }

  compareCategoryAgent(o1: Pick<ICategoryAgent, 'id'> | null, o2: Pick<ICategoryAgent, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategoryAgentIdentifier(o1) === this.getCategoryAgentIdentifier(o2) : o1 === o2;
  }

  addCategoryAgentToCollectionIfMissing<Type extends Pick<ICategoryAgent, 'id'>>(
    categoryAgentCollection: Type[],
    ...categoryAgentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categoryAgents: Type[] = categoryAgentsToCheck.filter(isPresent);
    if (categoryAgents.length > 0) {
      const categoryAgentCollectionIdentifiers = categoryAgentCollection.map(
        categoryAgentItem => this.getCategoryAgentIdentifier(categoryAgentItem)!
      );
      const categoryAgentsToAdd = categoryAgents.filter(categoryAgentItem => {
        const categoryAgentIdentifier = this.getCategoryAgentIdentifier(categoryAgentItem);
        if (categoryAgentCollectionIdentifiers.includes(categoryAgentIdentifier)) {
          return false;
        }
        categoryAgentCollectionIdentifiers.push(categoryAgentIdentifier);
        return true;
      });
      return [...categoryAgentsToAdd, ...categoryAgentCollection];
    }
    return categoryAgentCollection;
  }
}
