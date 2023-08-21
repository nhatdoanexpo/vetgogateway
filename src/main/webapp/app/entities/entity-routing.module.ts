import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'config-app',
        data: { pageTitle: 'ConfigApps' },
        loadChildren: () => import('./config-app/config-app.module').then(m => m.ConfigAppModule),
      },
      {
        path: 'item',
        data: { pageTitle: 'Items' },
        loadChildren: () => import('./item/item.module').then(m => m.ItemModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'Categories' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'agent',
        data: { pageTitle: 'Agents' },
        loadChildren: () => import('./agent/agent.module').then(m => m.AgentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
