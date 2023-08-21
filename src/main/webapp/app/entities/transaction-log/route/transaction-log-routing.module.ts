import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransactionLogComponent } from '../list/transaction-log.component';
import { TransactionLogDetailComponent } from '../detail/transaction-log-detail.component';
import { TransactionLogUpdateComponent } from '../update/transaction-log-update.component';
import { TransactionLogRoutingResolveService } from './transaction-log-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const transactionLogRoute: Routes = [
  {
    path: '',
    component: TransactionLogComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransactionLogDetailComponent,
    resolve: {
      transactionLog: TransactionLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransactionLogUpdateComponent,
    resolve: {
      transactionLog: TransactionLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransactionLogUpdateComponent,
    resolve: {
      transactionLog: TransactionLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transactionLogRoute)],
  exports: [RouterModule],
})
export class TransactionLogRoutingModule {}
