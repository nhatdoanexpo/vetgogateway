import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DistributorDetailComponent } from './distributor-detail.component';

describe('Distributor Management Detail Component', () => {
  let comp: DistributorDetailComponent;
  let fixture: ComponentFixture<DistributorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DistributorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ distributor: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DistributorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DistributorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load distributor on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.distributor).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
