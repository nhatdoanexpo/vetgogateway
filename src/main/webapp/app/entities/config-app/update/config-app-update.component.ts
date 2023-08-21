import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ConfigAppFormService, ConfigAppFormGroup } from './config-app-form.service';
import { IConfigApp } from '../config-app.model';
import { ConfigAppService } from '../service/config-app.service';

@Component({
  selector: 'jhi-config-app-update',
  templateUrl: './config-app-update.component.html',
})
export class ConfigAppUpdateComponent implements OnInit {
  isSaving = false;
  configApp: IConfigApp | null = null;

  editForm: ConfigAppFormGroup = this.configAppFormService.createConfigAppFormGroup();

  constructor(
    protected configAppService: ConfigAppService,
    protected configAppFormService: ConfigAppFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ configApp }) => {
      this.configApp = configApp;
      if (configApp) {
        this.updateForm(configApp);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const configApp = this.configAppFormService.getConfigApp(this.editForm);
    if (configApp.id !== null) {
      this.subscribeToSaveResponse(this.configAppService.update(configApp));
    } else {
      this.subscribeToSaveResponse(this.configAppService.create(configApp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConfigApp>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(configApp: IConfigApp): void {
    this.configApp = configApp;
    this.configAppFormService.resetForm(this.editForm, configApp);
  }
}
