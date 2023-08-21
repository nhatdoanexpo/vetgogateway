import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDistributor, NewDistributor } from '../distributor.model';

export type PartialUpdateDistributor = Partial<IDistributor> & Pick<IDistributor, 'id'>;

export type EntityResponseType = HttpResponse<IDistributor>;
export type EntityArrayResponseType = HttpResponse<IDistributor[]>;

@Injectable({ providedIn: 'root' })
export class DistributorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/distributors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(distributor: NewDistributor): Observable<EntityResponseType> {
    return this.http.post<IDistributor>(this.resourceUrl, distributor, { observe: 'response' });
  }

  update(distributor: IDistributor): Observable<EntityResponseType> {
    return this.http.put<IDistributor>(`${this.resourceUrl}/${this.getDistributorIdentifier(distributor)}`, distributor, {
      observe: 'response',
    });
  }

  partialUpdate(distributor: PartialUpdateDistributor): Observable<EntityResponseType> {
    return this.http.patch<IDistributor>(`${this.resourceUrl}/${this.getDistributorIdentifier(distributor)}`, distributor, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDistributor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistributor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDistributorIdentifier(distributor: Pick<IDistributor, 'id'>): number {
    return distributor.id;
  }

  compareDistributor(o1: Pick<IDistributor, 'id'> | null, o2: Pick<IDistributor, 'id'> | null): boolean {
    return o1 && o2 ? this.getDistributorIdentifier(o1) === this.getDistributorIdentifier(o2) : o1 === o2;
  }

  addDistributorToCollectionIfMissing<Type extends Pick<IDistributor, 'id'>>(
    distributorCollection: Type[],
    ...distributorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const distributors: Type[] = distributorsToCheck.filter(isPresent);
    if (distributors.length > 0) {
      const distributorCollectionIdentifiers = distributorCollection.map(
        distributorItem => this.getDistributorIdentifier(distributorItem)!
      );
      const distributorsToAdd = distributors.filter(distributorItem => {
        const distributorIdentifier = this.getDistributorIdentifier(distributorItem);
        if (distributorCollectionIdentifiers.includes(distributorIdentifier)) {
          return false;
        }
        distributorCollectionIdentifiers.push(distributorIdentifier);
        return true;
      });
      return [...distributorsToAdd, ...distributorCollection];
    }
    return distributorCollection;
  }
}
