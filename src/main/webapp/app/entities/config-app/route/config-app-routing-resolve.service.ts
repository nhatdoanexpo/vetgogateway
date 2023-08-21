import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConfigApp } from '../config-app.model';
import { ConfigAppService } from '../service/config-app.service';

@Injectable({ providedIn: 'root' })
export class ConfigAppRoutingResolveService implements Resolve<IConfigApp | null> {
  constructor(protected service: ConfigAppService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConfigApp | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((configApp: HttpResponse<IConfigApp>) => {
          if (configApp.body) {
            return of(configApp.body);
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
