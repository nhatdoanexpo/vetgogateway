import { Routes } from '@angular/router';

import { ErrorComponent } from './error.component';

export const errorRoute: Routes = [
  {
    path: 'error',
    component: ErrorComponent,
    data: {
      pageTitle: 'Trang thông tin lỗi!',
    },
  },
  {
    path: 'accessdenied',
    component: ErrorComponent,
    data: {
      pageTitle: 'Trang thông tin lỗi!',
      errorMessage: 'Bạn không có quyền xem trang này.',
    },
  },
  {
    path: '404',
    component: ErrorComponent,
    data: {
      pageTitle: 'Trang thông tin lỗi!',
      errorMessage: 'Trang không tồn tại.',
    },
  },
  {
    path: '**',
    redirectTo: '/404',
  },
];
