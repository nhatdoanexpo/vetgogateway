import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VgOrderDetailComponent } from './vg-order-detail.component';

describe('VgOrder Management Detail Component', () => {
  let comp: VgOrderDetailComponent;
  let fixture: ComponentFixture<VgOrderDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VgOrderDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ vgOrder: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VgOrderDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VgOrderDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load vgOrder on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.vgOrder).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
