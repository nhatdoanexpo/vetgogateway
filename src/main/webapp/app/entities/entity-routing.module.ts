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
      {
        path: 'category-agent',
        data: { pageTitle: 'CategoryAgents' },
        loadChildren: () => import('./category-agent/category-agent.module').then(m => m.CategoryAgentModule),
      },
      {
        path: 'item-agent',
        data: { pageTitle: 'ItemAgents' },
        loadChildren: () => import('./item-agent/item-agent.module').then(m => m.ItemAgentModule),
      },
      {
        path: 'transaction-log',
        data: { pageTitle: 'TransactionLogs' },
        loadChildren: () => import('./transaction-log/transaction-log.module').then(m => m.TransactionLogModule),
      },
      {
        path: 'distributor',
        data: { pageTitle: 'Distributors' },
        loadChildren: () => import('./distributor/distributor.module').then(m => m.DistributorModule),
      },
      {
        path: 'partner',
        data: { pageTitle: 'Partners' },
        loadChildren: () => import('./partner/partner.module').then(m => m.PartnerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
