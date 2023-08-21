import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistributor } from '../distributor.model';

@Component({
  selector: 'jhi-distributor-detail',
  templateUrl: './distributor-detail.component.html',
})
export class DistributorDetailComponent implements OnInit {
  distributor: IDistributor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ distributor }) => {
      this.distributor = distributor;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
