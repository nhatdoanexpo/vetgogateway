import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DistributorComponent } from './list/distributor.component';
import { DistributorDetailComponent } from './detail/distributor-detail.component';
import { DistributorUpdateComponent } from './update/distributor-update.component';
import { DistributorDeleteDialogComponent } from './delete/distributor-delete-dialog.component';
import { DistributorRoutingModule } from './route/distributor-routing.module';

@NgModule({
  imports: [SharedModule, DistributorRoutingModule],
  declarations: [DistributorComponent, DistributorDetailComponent, DistributorUpdateComponent, DistributorDeleteDialogComponent],
})
export class DistributorModule {}
