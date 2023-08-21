import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConfigAppComponent } from '../list/config-app.component';
import { ConfigAppDetailComponent } from '../detail/config-app-detail.component';
import { ConfigAppUpdateComponent } from '../update/config-app-update.component';
import { ConfigAppRoutingResolveService } from './config-app-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const configAppRoute: Routes = [
  {
    path: '',
    component: ConfigAppComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConfigAppDetailComponent,
    resolve: {
      configApp: ConfigAppRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConfigAppUpdateComponent,
    resolve: {
      configApp: ConfigAppRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConfigAppUpdateComponent,
    resolve: {
      configApp: ConfigAppRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(configAppRoute)],
  exports: [RouterModule],
})
export class ConfigAppRoutingModule {}
