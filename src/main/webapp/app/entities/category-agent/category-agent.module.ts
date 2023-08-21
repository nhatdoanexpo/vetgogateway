import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategoryAgentComponent } from './list/category-agent.component';
import { CategoryAgentDetailComponent } from './detail/category-agent-detail.component';
import { CategoryAgentUpdateComponent } from './update/category-agent-update.component';
import { CategoryAgentDeleteDialogComponent } from './delete/category-agent-delete-dialog.component';
import { CategoryAgentRoutingModule } from './route/category-agent-routing.module';

@NgModule({
  imports: [SharedModule, CategoryAgentRoutingModule],
  declarations: [CategoryAgentComponent, CategoryAgentDetailComponent, CategoryAgentUpdateComponent, CategoryAgentDeleteDialogComponent],
})
export class CategoryAgentModule {}
