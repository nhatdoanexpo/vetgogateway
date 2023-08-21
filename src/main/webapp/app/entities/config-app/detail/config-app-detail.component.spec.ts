import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConfigAppDetailComponent } from './config-app-detail.component';

describe('ConfigApp Management Detail Component', () => {
  let comp: ConfigAppDetailComponent;
  let fixture: ComponentFixture<ConfigAppDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfigAppDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ configApp: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ConfigAppDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConfigAppDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load configApp on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.configApp).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
