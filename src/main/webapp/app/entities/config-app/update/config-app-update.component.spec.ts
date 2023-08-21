import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ConfigAppFormService } from './config-app-form.service';
import { ConfigAppService } from '../service/config-app.service';
import { IConfigApp } from '../config-app.model';

import { ConfigAppUpdateComponent } from './config-app-update.component';

describe('ConfigApp Management Update Component', () => {
  let comp: ConfigAppUpdateComponent;
  let fixture: ComponentFixture<ConfigAppUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let configAppFormService: ConfigAppFormService;
  let configAppService: ConfigAppService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ConfigAppUpdateComponent],
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
      .overrideTemplate(ConfigAppUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConfigAppUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    configAppFormService = TestBed.inject(ConfigAppFormService);
    configAppService = TestBed.inject(ConfigAppService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const configApp: IConfigApp = { id: 456 };

      activatedRoute.data = of({ configApp });
      comp.ngOnInit();

      expect(comp.configApp).toEqual(configApp);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConfigApp>>();
      const configApp = { id: 123 };
      jest.spyOn(configAppFormService, 'getConfigApp').mockReturnValue(configApp);
      jest.spyOn(configAppService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configApp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: configApp }));
      saveSubject.complete();

      // THEN
      expect(configAppFormService.getConfigApp).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(configAppService.update).toHaveBeenCalledWith(expect.objectContaining(configApp));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConfigApp>>();
      const configApp = { id: 123 };
      jest.spyOn(configAppFormService, 'getConfigApp').mockReturnValue({ id: null });
      jest.spyOn(configAppService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configApp: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: configApp }));
      saveSubject.complete();

      // THEN
      expect(configAppFormService.getConfigApp).toHaveBeenCalled();
      expect(configAppService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConfigApp>>();
      const configApp = { id: 123 };
      jest.spyOn(configAppService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configApp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(configAppService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
