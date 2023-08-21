import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoryAgent } from '../category-agent.model';
import { CategoryAgentService } from '../service/category-agent.service';

@Injectable({ providedIn: 'root' })
export class CategoryAgentRoutingResolveService implements Resolve<ICategoryAgent | null> {
  constructor(protected service: CategoryAgentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoryAgent | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categoryAgent: HttpResponse<ICategoryAgent>) => {
          if (categoryAgent.body) {
            return of(categoryAgent.body);
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
