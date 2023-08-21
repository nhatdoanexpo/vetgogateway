import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VgOrderComponent } from '../list/vg-order.component';
import { VgOrderDetailComponent } from '../detail/vg-order-detail.component';
import { VgOrderUpdateComponent } from '../update/vg-order-update.component';
import { VgOrderRoutingResolveService } from './vg-order-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const vgOrderRoute: Routes = [
  {
    path: '',
    component: VgOrderComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VgOrderDetailComponent,
    resolve: {
      vgOrder: VgOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VgOrderUpdateComponent,
    resolve: {
      vgOrder: VgOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VgOrderUpdateComponent,
    resolve: {
      vgOrder: VgOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vgOrderRoute)],
  exports: [RouterModule],
})
export class VgOrderRoutingModule {}
