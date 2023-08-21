import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVgOrder } from '../vg-order.model';
import { VgOrderService } from '../service/vg-order.service';

@Injectable({ providedIn: 'root' })
export class VgOrderRoutingResolveService implements Resolve<IVgOrder | null> {
  constructor(protected service: VgOrderService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVgOrder | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vgOrder: HttpResponse<IVgOrder>) => {
          if (vgOrder.body) {
            return of(vgOrder.body);
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
