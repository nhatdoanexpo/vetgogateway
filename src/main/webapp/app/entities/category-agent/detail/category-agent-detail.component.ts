import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoryAgent } from '../category-agent.model';

@Component({
  selector: 'jhi-category-agent-detail',
  templateUrl: './category-agent-detail.component.html',
})
export class CategoryAgentDetailComponent implements OnInit {
  categoryAgent: ICategoryAgent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoryAgent }) => {
      this.categoryAgent = categoryAgent;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
