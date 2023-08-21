import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IItemAgent } from '../item-agent.model';
import { ItemAgentService } from '../service/item-agent.service';

@Injectable({ providedIn: 'root' })
export class ItemAgentRoutingResolveService implements Resolve<IItemAgent | null> {
  constructor(protected service: ItemAgentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemAgent | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((itemAgent: HttpResponse<IItemAgent>) => {
          if (itemAgent.body) {
            return of(itemAgent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
