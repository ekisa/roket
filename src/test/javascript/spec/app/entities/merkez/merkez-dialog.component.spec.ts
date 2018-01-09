/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { MerkezDialogComponent } from '../../../../../../main/webapp/app/entities/merkez/merkez-dialog.component';
import { MerkezService } from '../../../../../../main/webapp/app/entities/merkez/merkez.service';
import { Merkez } from '../../../../../../main/webapp/app/entities/merkez/merkez.model';
import { MahalleService } from '../../../../../../main/webapp/app/entities/mahalle';
import { GPSLokasyonService } from '../../../../../../main/webapp/app/entities/gps-lokasyon';
import { AdresService } from '../../../../../../main/webapp/app/entities/adres';

describe('Component Tests', () => {

    describe('Merkez Management Dialog Component', () => {
        let comp: MerkezDialogComponent;
        let fixture: ComponentFixture<MerkezDialogComponent>;
        let service: MerkezService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MerkezDialogComponent],
                providers: [
                    MahalleService,
                    GPSLokasyonService,
                    AdresService,
                    MerkezService
                ]
            })
            .overrideTemplate(MerkezDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerkezDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerkezService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Merkez(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.merkez = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'merkezListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Merkez();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.merkez = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'merkezListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
