import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DistributorFormService } from './distributor-form.service';
import { DistributorService } from '../service/distributor.service';
import { IDistributor } from '../distributor.model';

import { DistributorUpdateComponent } from './distributor-update.component';

describe('Distributor Management Update Component', () => {
  let comp: DistributorUpdateComponent;
  let fixture: ComponentFixture<DistributorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let distributorFormService: DistributorFormService;
  let distributorService: DistributorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DistributorUpdateComponent],
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
      .overrideTemplate(DistributorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DistributorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    distributorFormService = TestBed.inject(DistributorFormService);
    distributorService = TestBed.inject(DistributorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const distributor: IDistributor = { id: 456 };

      activatedRoute.data = of({ distributor });
      comp.ngOnInit();

      expect(comp.distributor).toEqual(distributor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistributor>>();
      const distributor = { id: 123 };
      jest.spyOn(distributorFormService, 'getDistributor').mockReturnValue(distributor);
      jest.spyOn(distributorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ distributor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: distributor }));
      saveSubject.complete();

      // THEN
      expect(distributorFormService.getDistributor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(distributorService.update).toHaveBeenCalledWith(expect.objectContaining(distributor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistributor>>();
      const distributor = { id: 123 };
      jest.spyOn(distributorFormService, 'getDistributor').mockReturnValue({ id: null });
      jest.spyOn(distributorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ distributor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: distributor }));
      saveSubject.complete();

      // THEN
      expect(distributorFormService.getDistributor).toHaveBeenCalled();
      expect(distributorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDistributor>>();
      const distributor = { id: 123 };
      jest.spyOn(distributorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ distributor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(distributorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
