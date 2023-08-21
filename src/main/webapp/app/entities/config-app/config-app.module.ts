import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ConfigAppComponent } from './list/config-app.component';
import { ConfigAppDetailComponent } from './detail/config-app-detail.component';
import { ConfigAppUpdateComponent } from './update/config-app-update.component';
import { ConfigAppDeleteDialogComponent } from './delete/config-app-delete-dialog.component';
import { ConfigAppRoutingModule } from './route/config-app-routing.module';

@NgModule({
  imports: [SharedModule, ConfigAppRoutingModule],
  declarations: [ConfigAppComponent, ConfigAppDetailComponent, ConfigAppUpdateComponent, ConfigAppDeleteDialogComponent],
})
export class ConfigAppModule {}
