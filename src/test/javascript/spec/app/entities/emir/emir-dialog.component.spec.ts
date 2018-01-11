/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { EmirDialogComponent } from '../../../../../../main/webapp/app/entities/emir/emir-dialog.component';
import { EmirService } from '../../../../../../main/webapp/app/entities/emir/emir.service';
import { Emir } from '../../../../../../main/webapp/app/entities/emir/emir.model';
import { IsyeriService } from '../../../../../../main/webapp/app/entities/isyeri';
import { AdresService } from '../../../../../../main/webapp/app/entities/adres';
import { GPSLokasyonService } from '../../../../../../main/webapp/app/entities/gps-lokasyon';
import { FaturaService } from '../../../../../../main/webapp/app/entities/fatura';

describe('Component Tests', () => {

    describe('Emir Management Dialog Component', () => {
        let comp: EmirDialogComponent;
        let fixture: ComponentFixture<EmirDialogComponent>;
        let service: EmirService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [EmirDialogComponent],
                providers: [
                    IsyeriService,
                    AdresService,
                    GPSLokasyonService,
                    FaturaService,
                    EmirService
                ]
            })
            .overrideTemplate(EmirDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmirDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmirService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Emir(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.emir = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'emirListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Emir();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.emir = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'emirListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
