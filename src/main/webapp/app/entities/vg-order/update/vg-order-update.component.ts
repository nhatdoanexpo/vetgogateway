import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { VgOrderFormService, VgOrderFormGroup } from './vg-order-form.service';
import { IVgOrder } from '../vg-order.model';
import { VgOrderService } from '../service/vg-order.service';

@Component({
  selector: 'jhi-vg-order-update',
  templateUrl: './vg-order-update.component.html',
})
export class VgOrderUpdateComponent implements OnInit {
  isSaving = false;
  vgOrder: IVgOrder | null = null;

  editForm: VgOrderFormGroup = this.vgOrderFormService.createVgOrderFormGroup();

  constructor(
    protected vgOrderService: VgOrderService,
    protected vgOrderFormService: VgOrderFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vgOrder }) => {
      this.vgOrder = vgOrder;
      if (vgOrder) {
        this.updateForm(vgOrder);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vgOrder = this.vgOrderFormService.getVgOrder(this.editForm);
    if (vgOrder.id !== null) {
      this.subscribeToSaveResponse(this.vgOrderService.update(vgOrder));
    } else {
      this.subscribeToSaveResponse(this.vgOrderService.create(vgOrder));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVgOrder>>): void {
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

  protected updateForm(vgOrder: IVgOrder): void {
    this.vgOrder = vgOrder;
    this.vgOrderFormService.resetForm(this.editForm, vgOrder);
  }
}
