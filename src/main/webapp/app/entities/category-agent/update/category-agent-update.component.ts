import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CategoryAgentFormService, CategoryAgentFormGroup } from './category-agent-form.service';
import { ICategoryAgent } from '../category-agent.model';
import { CategoryAgentService } from '../service/category-agent.service';

@Component({
  selector: 'jhi-category-agent-update',
  templateUrl: './category-agent-update.component.html',
})
export class CategoryAgentUpdateComponent implements OnInit {
  isSaving = false;
  categoryAgent: ICategoryAgent | null = null;

  editForm: CategoryAgentFormGroup = this.categoryAgentFormService.createCategoryAgentFormGroup();

  constructor(
    protected categoryAgentService: CategoryAgentService,
    protected categoryAgentFormService: CategoryAgentFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoryAgent }) => {
      this.categoryAgent = categoryAgent;
      if (categoryAgent) {
        this.updateForm(categoryAgent);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoryAgent = this.categoryAgentFormService.getCategoryAgent(this.editForm);
    if (categoryAgent.id !== null) {
      this.subscribeToSaveResponse(this.categoryAgentService.update(categoryAgent));
    } else {
      this.subscribeToSaveResponse(this.categoryAgentService.create(categoryAgent));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoryAgent>>): void {
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

  protected updateForm(categoryAgent: ICategoryAgent): void {
    this.categoryAgent = categoryAgent;
    this.categoryAgentFormService.resetForm(this.editForm, categoryAgent);
  }
}
