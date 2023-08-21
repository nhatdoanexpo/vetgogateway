import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ItemAgentFormService } from './item-agent-form.service';
import { ItemAgentService } from '../service/item-agent.service';
import { IItemAgent } from '../item-agent.model';

import { ItemAgentUpdateComponent } from './item-agent-update.component';

describe('ItemAgent Management Update Component', () => {
  let comp: ItemAgentUpdateComponent;
  let fixture: ComponentFixture<ItemAgentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let itemAgentFormService: ItemAgentFormService;
  let itemAgentService: ItemAgentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ItemAgentUpdateComponent],
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
      .overrideTemplate(ItemAgentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ItemAgentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    itemAgentFormService = TestBed.inject(ItemAgentFormService);
    itemAgentService = TestBed.inject(ItemAgentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const itemAgent: IItemAgent = { id: 456 };

      activatedRoute.data = of({ itemAgent });
      comp.ngOnInit();

      expect(comp.itemAgent).toEqual(itemAgent);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IItemAgent>>();
      const itemAgent = { id: 123 };
      jest.spyOn(itemAgentFormService, 'getItemAgent').mockReturnValue(itemAgent);
      jest.spyOn(itemAgentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemAgent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemAgent }));
      saveSubject.complete();

      // THEN
      expect(itemAgentFormService.getItemAgent).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(itemAgentService.update).toHaveBeenCalledWith(expect.objectContaining(itemAgent));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IItemAgent>>();
      const itemAgent = { id: 123 };
      jest.spyOn(itemAgentFormService, 'getItemAgent').mockReturnValue({ id: null });
      jest.spyOn(itemAgentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemAgent: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemAgent }));
      saveSubject.complete();

      // THEN
      expect(itemAgentFormService.getItemAgent).toHaveBeenCalled();
      expect(itemAgentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IItemAgent>>();
      const itemAgent = { id: 123 };
      jest.spyOn(itemAgentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemAgent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(itemAgentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
