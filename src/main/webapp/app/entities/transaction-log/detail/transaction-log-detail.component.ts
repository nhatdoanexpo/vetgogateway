import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionLog } from '../transaction-log.model';

@Component({
  selector: 'jhi-transaction-log-detail',
  templateUrl: './transaction-log-detail.component.html',
})
export class TransactionLogDetailComponent implements OnInit {
  transactionLog: ITransactionLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionLog }) => {
      this.transactionLog = transactionLog;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
