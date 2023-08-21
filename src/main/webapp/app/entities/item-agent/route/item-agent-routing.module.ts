import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ItemAgentComponent } from '../list/item-agent.component';
import { ItemAgentDetailComponent } from '../detail/item-agent-detail.component';
import { ItemAgentUpdateComponent } from '../update/item-agent-update.component';
import { ItemAgentRoutingResolveService } from './item-agent-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const itemAgentRoute: Routes = [
  {
    path: '',
    component: ItemAgentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemAgentDetailComponent,
    resolve: {
      itemAgent: ItemAgentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemAgentUpdateComponent,
    resolve: {
      itemAgent: ItemAgentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemAgentUpdateComponent,
    resolve: {
      itemAgent: ItemAgentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(itemAgentRoute)],
  exports: [RouterModule],
})
export class ItemAgentRoutingModule {}
