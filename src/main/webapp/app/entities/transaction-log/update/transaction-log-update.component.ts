import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TransactionLogFormService, TransactionLogFormGroup } from './transaction-log-form.service';
import { ITransactionLog } from '../transaction-log.model';
import { TransactionLogService } from '../service/transaction-log.service';

@Component({
  selector: 'jhi-transaction-log-update',
  templateUrl: './transaction-log-update.component.html',
})
export class TransactionLogUpdateComponent implements OnInit {
  isSaving = false;
  transactionLog: ITransactionLog | null = null;

  editForm: TransactionLogFormGroup = this.transactionLogFormService.createTransactionLogFormGroup();

  constructor(
    protected transactionLogService: TransactionLogService,
    protected transactionLogFormService: TransactionLogFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionLog }) => {
      this.transactionLog = transactionLog;
      if (transactionLog) {
        this.updateForm(transactionLog);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionLog = this.transactionLogFormService.getTransactionLog(this.editForm);
    if (transactionLog.id !== null) {
      this.subscribeToSaveResponse(this.transactionLogService.update(transactionLog));
    } else {
      this.subscribeToSaveResponse(this.transactionLogService.create(transactionLog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionLog>>): void {
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

  protected updateForm(transactionLog: ITransactionLog): void {
    this.transactionLog = transactionLog;
    this.transactionLogFormService.resetForm(this.editForm, transactionLog);
  }
}
