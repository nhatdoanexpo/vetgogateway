import { Route } from '@angular/router';

import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    pageTitle: 'Chào mừng bạn đến với Java Hipster!',
  },
};
