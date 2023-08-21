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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
