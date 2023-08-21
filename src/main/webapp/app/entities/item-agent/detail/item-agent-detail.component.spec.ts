import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ItemAgentDetailComponent } from './item-agent-detail.component';

describe('ItemAgent Management Detail Component', () => {
  let comp: ItemAgentDetailComponent;
  let fixture: ComponentFixture<ItemAgentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ItemAgentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ itemAgent: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ItemAgentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ItemAgentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load itemAgent on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.itemAgent).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
