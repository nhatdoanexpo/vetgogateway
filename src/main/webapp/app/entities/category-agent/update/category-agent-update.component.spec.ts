import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategoryAgentFormService } from './category-agent-form.service';
import { CategoryAgentService } from '../service/category-agent.service';
import { ICategoryAgent } from '../category-agent.model';

import { CategoryAgentUpdateComponent } from './category-agent-update.component';

describe('CategoryAgent Management Update Component', () => {
  let comp: CategoryAgentUpdateComponent;
  let fixture: ComponentFixture<CategoryAgentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoryAgentFormService: CategoryAgentFormService;
  let categoryAgentService: CategoryAgentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategoryAgentUpdateComponent],
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
      .overrideTemplate(CategoryAgentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoryAgentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoryAgentFormService = TestBed.inject(CategoryAgentFormService);
    categoryAgentService = TestBed.inject(CategoryAgentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categoryAgent: ICategoryAgent = { id: 456 };

      activatedRoute.data = of({ categoryAgent });
      comp.ngOnInit();

      expect(comp.categoryAgent).toEqual(categoryAgent);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoryAgent>>();
      const categoryAgent = { id: 123 };
      jest.spyOn(categoryAgentFormService, 'getCategoryAgent').mockReturnValue(categoryAgent);
      jest.spyOn(categoryAgentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoryAgent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoryAgent }));
      saveSubject.complete();

      // THEN
      expect(categoryAgentFormService.getCategoryAgent).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoryAgentService.update).toHaveBeenCalledWith(expect.objectContaining(categoryAgent));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoryAgent>>();
      const categoryAgent = { id: 123 };
      jest.spyOn(categoryAgentFormService, 'getCategoryAgent').mockReturnValue({ id: null });
      jest.spyOn(categoryAgentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoryAgent: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoryAgent }));
      saveSubject.complete();

      // THEN
      expect(categoryAgentFormService.getCategoryAgent).toHaveBeenCalled();
      expect(categoryAgentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoryAgent>>();
      const categoryAgent = { id: 123 };
      jest.spyOn(categoryAgentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoryAgent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoryAgentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
