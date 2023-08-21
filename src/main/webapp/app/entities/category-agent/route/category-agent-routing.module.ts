import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategoryAgentComponent } from '../list/category-agent.component';
import { CategoryAgentDetailComponent } from '../detail/category-agent-detail.component';
import { CategoryAgentUpdateComponent } from '../update/category-agent-update.component';
import { CategoryAgentRoutingResolveService } from './category-agent-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categoryAgentRoute: Routes = [
  {
    path: '',
    component: CategoryAgentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoryAgentDetailComponent,
    resolve: {
      categoryAgent: CategoryAgentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoryAgentUpdateComponent,
    resolve: {
      categoryAgent: CategoryAgentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoryAgentUpdateComponent,
    resolve: {
      categoryAgent: CategoryAgentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categoryAgentRoute)],
  exports: [RouterModule],
})
export class CategoryAgentRoutingModule {}
