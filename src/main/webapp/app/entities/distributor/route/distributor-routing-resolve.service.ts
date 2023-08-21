import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDistributor } from '../distributor.model';
import { DistributorService } from '../service/distributor.service';

@Injectable({ providedIn: 'root' })
export class DistributorRoutingResolveService implements Resolve<IDistributor | null> {
  constructor(protected service: DistributorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistributor | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((distributor: HttpResponse<IDistributor>) => {
          if (distributor.body) {
            return of(distributor.body);
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
