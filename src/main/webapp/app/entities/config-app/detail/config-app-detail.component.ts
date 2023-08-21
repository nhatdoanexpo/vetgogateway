import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConfigApp } from '../config-app.model';

@Component({
  selector: 'jhi-config-app-detail',
  templateUrl: './config-app-detail.component.html',
})
export class ConfigAppDetailComponent implements OnInit {
  configApp: IConfigApp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ configApp }) => {
      this.configApp = configApp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
