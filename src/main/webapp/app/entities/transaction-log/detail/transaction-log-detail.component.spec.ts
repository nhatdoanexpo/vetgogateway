import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransactionLogDetailComponent } from './transaction-log-detail.component';

describe('TransactionLog Management Detail Component', () => {
  let comp: TransactionLogDetailComponent;
  let fixture: ComponentFixture<TransactionLogDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransactionLogDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transactionLog: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransactionLogDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransactionLogDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transactionLog on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transactionLog).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
