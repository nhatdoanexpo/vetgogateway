import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VgOrderFormService } from './vg-order-form.service';
import { VgOrderService } from '../service/vg-order.service';
import { IVgOrder } from '../vg-order.model';

import { VgOrderUpdateComponent } from './vg-order-update.component';

describe('VgOrder Management Update Component', () => {
  let comp: VgOrderUpdateComponent;
  let fixture: ComponentFixture<VgOrderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vgOrderFormService: VgOrderFormService;
  let vgOrderService: VgOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VgOrderUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VgOrderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VgOrderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vgOrderFormService = TestBed.inject(VgOrderFormService);
    vgOrderService = TestBed.inject(VgOrderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const vgOrder: IVgOrder = { id: 456 };

      activatedRoute.data = of({ vgOrder });
      comp.ngOnInit();

      expect(comp.vgOrder).toEqual(vgOrder);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVgOrder>>();
      const vgOrder = { id: 123 };
      jest.spyOn(vgOrderFormService, 'getVgOrder').mockReturnValue(vgOrder);
      jest.spyOn(vgOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vgOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vgOrder }));
      saveSubject.complete();

      // THEN
      expect(vgOrderFormService.getVgOrder).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(vgOrderService.update).toHaveBeenCalledWith(expect.objectContaining(vgOrder));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVgOrder>>();
      const vgOrder = { id: 123 };
      jest.spyOn(vgOrderFormService, 'getVgOrder').mockReturnValue({ id: null });
      jest.spyOn(vgOrderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vgOrder: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vgOrder }));
      saveSubject.complete();

      // THEN
      expect(vgOrderFormService.getVgOrder).toHaveBeenCalled();
      expect(vgOrderService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVgOrder>>();
      const vgOrder = { id: 123 };
      jest.spyOn(vgOrderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vgOrder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vgOrderService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
