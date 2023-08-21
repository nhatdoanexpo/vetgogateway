import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DistributorFormService, DistributorFormGroup } from './distributor-form.service';
import { IDistributor } from '../distributor.model';
import { DistributorService } from '../service/distributor.service';

@Component({
  selector: 'jhi-distributor-update',
  templateUrl: './distributor-update.component.html',
})
export class DistributorUpdateComponent implements OnInit {
  isSaving = false;
  distributor: IDistributor | null = null;

  editForm: DistributorFormGroup = this.distributorFormService.createDistributorFormGroup();

  constructor(
    protected distributorService: DistributorService,
    protected distributorFormService: DistributorFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ distributor }) => {
      this.distributor = distributor;
      if (distributor) {
        this.updateForm(distributor);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const distributor = this.distributorFormService.getDistributor(this.editForm);
    if (distributor.id !== null) {
      this.subscribeToSaveResponse(this.distributorService.update(distributor));
    } else {
      this.subscribeToSaveResponse(this.distributorService.create(distributor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistributor>>): void {
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

  protected updateForm(distributor: IDistributor): void {
    this.distributor = distributor;
    this.distributorFormService.resetForm(this.editForm, distributor);
  }
}
