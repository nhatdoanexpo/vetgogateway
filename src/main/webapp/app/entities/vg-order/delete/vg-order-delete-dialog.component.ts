import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVgOrder } from '../vg-order.model';
import { VgOrderService } from '../service/vg-order.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './vg-order-delete-dialog.component.html',
})
export class VgOrderDeleteDialogComponent {
  vgOrder?: IVgOrder;

  constructor(protected vgOrderService: VgOrderService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vgOrderService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
