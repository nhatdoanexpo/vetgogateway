import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITransactionLog } from '../transaction-log.model';
import { TransactionLogService } from '../service/transaction-log.service';

@Injectable({ providedIn: 'root' })
export class TransactionLogRoutingResolveService implements Resolve<ITransactionLog | null> {
  constructor(protected service: TransactionLogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransactionLog | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((transactionLog: HttpResponse<ITransactionLog>) => {
          if (transactionLog.body) {
            return of(transactionLog.body);
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
