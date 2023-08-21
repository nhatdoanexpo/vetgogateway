import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DistributorComponent } from '../list/distributor.component';
import { DistributorDetailComponent } from '../detail/distributor-detail.component';
import { DistributorUpdateComponent } from '../update/distributor-update.component';
import { DistributorRoutingResolveService } from './distributor-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const distributorRoute: Routes = [
  {
    path: '',
    component: DistributorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DistributorDetailComponent,
    resolve: {
      distributor: DistributorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DistributorUpdateComponent,
    resolve: {
      distributor: DistributorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DistributorUpdateComponent,
    resolve: {
      distributor: DistributorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(distributorRoute)],
  exports: [RouterModule],
})
export class DistributorRoutingModule {}
