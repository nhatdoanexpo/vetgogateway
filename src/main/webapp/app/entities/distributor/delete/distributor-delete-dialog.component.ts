import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDistributor } from '../distributor.model';
import { DistributorService } from '../service/distributor.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './distributor-delete-dialog.component.html',
})
export class DistributorDeleteDialogComponent {
  distributor?: IDistributor;

  constructor(protected distributorService: DistributorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.distributorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
