/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { FaturaDialogComponent } from '../../../../../../main/webapp/app/entities/fatura/fatura-dialog.component';
import { FaturaService } from '../../../../../../main/webapp/app/entities/fatura/fatura.service';
import { Fatura } from '../../../../../../main/webapp/app/entities/fatura/fatura.model';
import { MusteriService } from '../../../../../../main/webapp/app/entities/musteri';

describe('Component Tests', () => {

    describe('Fatura Management Dialog Component', () => {
        let comp: FaturaDialogComponent;
        let fixture: ComponentFixture<FaturaDialogComponent>;
        let service: FaturaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [FaturaDialogComponent],
                providers: [
                    MusteriService,
                    FaturaService
                ]
            })
            .overrideTemplate(FaturaDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FaturaDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaturaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Fatura(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.fatura = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'faturaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Fatura();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.fatura = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'faturaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
