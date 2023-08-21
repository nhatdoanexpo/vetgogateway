import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategoryAgentDetailComponent } from './category-agent-detail.component';

describe('CategoryAgent Management Detail Component', () => {
  let comp: CategoryAgentDetailComponent;
  let fixture: ComponentFixture<CategoryAgentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoryAgentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ categoryAgent: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CategoryAgentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CategoryAgentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load categoryAgent on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.categoryAgent).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
