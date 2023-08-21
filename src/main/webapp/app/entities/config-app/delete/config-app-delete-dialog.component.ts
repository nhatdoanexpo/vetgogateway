import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConfigApp } from '../config-app.model';
import { ConfigAppService } from '../service/config-app.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './config-app-delete-dialog.component.html',
})
export class ConfigAppDeleteDialogComponent {
  configApp?: IConfigApp;

  constructor(protected configAppService: ConfigAppService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.configAppService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
