import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IItemAgent } from '../item-agent.model';
import { ItemAgentService } from '../service/item-agent.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './item-agent-delete-dialog.component.html',
})
export class ItemAgentDeleteDialogComponent {
  itemAgent?: IItemAgent;

  constructor(protected itemAgentService: ItemAgentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemAgentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
