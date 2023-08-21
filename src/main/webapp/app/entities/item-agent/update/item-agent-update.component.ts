import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ItemAgentFormService, ItemAgentFormGroup } from './item-agent-form.service';
import { IItemAgent } from '../item-agent.model';
import { ItemAgentService } from '../service/item-agent.service';

@Component({
  selector: 'jhi-item-agent-update',
  templateUrl: './item-agent-update.component.html',
})
export class ItemAgentUpdateComponent implements OnInit {
  isSaving = false;
  itemAgent: IItemAgent | null = null;

  editForm: ItemAgentFormGroup = this.itemAgentFormService.createItemAgentFormGroup();

  constructor(
    protected itemAgentService: ItemAgentService,
    protected itemAgentFormService: ItemAgentFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemAgent }) => {
      this.itemAgent = itemAgent;
      if (itemAgent) {
        this.updateForm(itemAgent);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemAgent = this.itemAgentFormService.getItemAgent(this.editForm);
    if (itemAgent.id !== null) {
      this.subscribeToSaveResponse(this.itemAgentService.update(itemAgent));
    } else {
      this.subscribeToSaveResponse(this.itemAgentService.create(itemAgent));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemAgent>>): void {
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

  protected updateForm(itemAgent: IItemAgent): void {
    this.itemAgent = itemAgent;
    this.itemAgentFormService.resetForm(this.editForm, itemAgent);
  }
}
