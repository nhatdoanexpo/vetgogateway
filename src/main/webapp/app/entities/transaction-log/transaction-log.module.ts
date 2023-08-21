import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransactionLogComponent } from './list/transaction-log.component';
import { TransactionLogDetailComponent } from './detail/transaction-log-detail.component';
import { TransactionLogUpdateComponent } from './update/transaction-log-update.component';
import { TransactionLogDeleteDialogComponent } from './delete/transaction-log-delete-dialog.component';
import { TransactionLogRoutingModule } from './route/transaction-log-routing.module';

@NgModule({
  imports: [SharedModule, TransactionLogRoutingModule],
  declarations: [
    TransactionLogComponent,
    TransactionLogDetailComponent,
    TransactionLogUpdateComponent,
    TransactionLogDeleteDialogComponent,
  ],
})
export class TransactionLogModule {}
