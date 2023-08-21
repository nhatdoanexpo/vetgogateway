import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConfigApp, NewConfigApp } from '../config-app.model';

export type PartialUpdateConfigApp = Partial<IConfigApp> & Pick<IConfigApp, 'id'>;

export type EntityResponseType = HttpResponse<IConfigApp>;
export type EntityArrayResponseType = HttpResponse<IConfigApp[]>;

@Injectable({ providedIn: 'root' })
export class ConfigAppService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/config-apps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(configApp: NewConfigApp): Observable<EntityResponseType> {
    return this.http.post<IConfigApp>(this.resourceUrl, configApp, { observe: 'response' });
  }

  update(configApp: IConfigApp): Observable<EntityResponseType> {
    return this.http.put<IConfigApp>(`${this.resourceUrl}/${this.getConfigAppIdentifier(configApp)}`, configApp, { observe: 'response' });
  }

  partialUpdate(configApp: PartialUpdateConfigApp): Observable<EntityResponseType> {
    return this.http.patch<IConfigApp>(`${this.resourceUrl}/${this.getConfigAppIdentifier(configApp)}`, configApp, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConfigApp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConfigApp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getConfigAppIdentifier(configApp: Pick<IConfigApp, 'id'>): number {
    return configApp.id;
  }

  compareConfigApp(o1: Pick<IConfigApp, 'id'> | null, o2: Pick<IConfigApp, 'id'> | null): boolean {
    return o1 && o2 ? this.getConfigAppIdentifier(o1) === this.getConfigAppIdentifier(o2) : o1 === o2;
  }

  addConfigAppToCollectionIfMissing<Type extends Pick<IConfigApp, 'id'>>(
    configAppCollection: Type[],
    ...configAppsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const configApps: Type[] = configAppsToCheck.filter(isPresent);
    if (configApps.length > 0) {
      const configAppCollectionIdentifiers = configAppCollection.map(configAppItem => this.getConfigAppIdentifier(configAppItem)!);
      const configAppsToAdd = configApps.filter(configAppItem => {
        const configAppIdentifier = this.getConfigAppIdentifier(configAppItem);
        if (configAppCollectionIdentifiers.includes(configAppIdentifier)) {
          return false;
        }
        configAppCollectionIdentifiers.push(configAppIdentifier);
        return true;
      });
      return [...configAppsToAdd, ...configAppCollection];
    }
    return configAppCollection;
  }
}
