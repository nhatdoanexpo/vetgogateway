import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ItemAgentComponent } from './list/item-agent.component';
import { ItemAgentDetailComponent } from './detail/item-agent-detail.component';
import { ItemAgentUpdateComponent } from './update/item-agent-update.component';
import { ItemAgentDeleteDialogComponent } from './delete/item-agent-delete-dialog.component';
import { ItemAgentRoutingModule } from './route/item-agent-routing.module';

@NgModule({
  imports: [SharedModule, ItemAgentRoutingModule],
  declarations: [ItemAgentComponent, ItemAgentDetailComponent, ItemAgentUpdateComponent, ItemAgentDeleteDialogComponent],
})
export class ItemAgentModule {}
