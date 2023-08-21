import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemAgent } from '../item-agent.model';

@Component({
  selector: 'jhi-item-agent-detail',
  templateUrl: './item-agent-detail.component.html',
})
export class ItemAgentDetailComponent implements OnInit {
  itemAgent: IItemAgent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemAgent }) => {
      this.itemAgent = itemAgent;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
