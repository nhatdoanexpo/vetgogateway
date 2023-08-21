import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VgOrderComponent } from './list/vg-order.component';
import { VgOrderDetailComponent } from './detail/vg-order-detail.component';
import { VgOrderUpdateComponent } from './update/vg-order-update.component';
import { VgOrderDeleteDialogComponent } from './delete/vg-order-delete-dialog.component';
import { VgOrderRoutingModule } from './route/vg-order-routing.module';

@NgModule({
  imports: [SharedModule, VgOrderRoutingModule],
  declarations: [VgOrderComponent, VgOrderDetailComponent, VgOrderUpdateComponent, VgOrderDeleteDialogComponent],
})
export class VgOrderModule {}
