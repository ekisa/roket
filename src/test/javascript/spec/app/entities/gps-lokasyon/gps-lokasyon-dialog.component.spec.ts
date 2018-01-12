/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { GPSLokasyonDialogComponent } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon-dialog.component';
import { GPSLokasyonService } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon.service';
import { GPSLokasyon } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon.model';

describe('Component Tests', () => {

    describe('GPSLokasyon Management Dialog Component', () => {
        let comp: GPSLokasyonDialogComponent;
        let fixture: ComponentFixture<GPSLokasyonDialogComponent>;
        let service: GPSLokasyonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [GPSLokasyonDialogComponent],
                providers: [
                    GPSLokasyonService
                ]
            })
            .overrideTemplate(GPSLokasyonDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GPSLokasyonDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GPSLokasyonService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GPSLokasyon(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.gPSLokasyon = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'gPSLokasyonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GPSLokasyon();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.gPSLokasyon = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'gPSLokasyonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
