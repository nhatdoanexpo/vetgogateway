import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVgOrder } from '../vg-order.model';

@Component({
  selector: 'jhi-vg-order-detail',
  templateUrl: './vg-order-detail.component.html',
})
export class VgOrderDetailComponent implements OnInit {
  vgOrder: IVgOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vgOrder }) => {
      this.vgOrder = vgOrder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
