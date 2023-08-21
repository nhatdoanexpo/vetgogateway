import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoryAgent } from '../category-agent.model';
import { CategoryAgentService } from '../service/category-agent.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './category-agent-delete-dialog.component.html',
})
export class CategoryAgentDeleteDialogComponent {
  categoryAgent?: ICategoryAgent;

  constructor(protected categoryAgentService: CategoryAgentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoryAgentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
