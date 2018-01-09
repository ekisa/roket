/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { IsyeriDialogComponent } from '../../../../../../main/webapp/app/entities/isyeri/isyeri-dialog.component';
import { IsyeriService } from '../../../../../../main/webapp/app/entities/isyeri/isyeri.service';
import { Isyeri } from '../../../../../../main/webapp/app/entities/isyeri/isyeri.model';
import { MerkezService } from '../../../../../../main/webapp/app/entities/merkez';
import { AdresService } from '../../../../../../main/webapp/app/entities/adres';
import { GPSLokasyonService } from '../../../../../../main/webapp/app/entities/gps-lokasyon';
import { MusteriService } from '../../../../../../main/webapp/app/entities/musteri';

describe('Component Tests', () => {

    describe('Isyeri Management Dialog Component', () => {
        let comp: IsyeriDialogComponent;
        let fixture: ComponentFixture<IsyeriDialogComponent>;
        let service: IsyeriService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IsyeriDialogComponent],
                providers: [
                    MerkezService,
                    AdresService,
                    GPSLokasyonService,
                    MusteriService,
                    IsyeriService
                ]
            })
            .overrideTemplate(IsyeriDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IsyeriDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IsyeriService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Isyeri(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.isyeri = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'isyeriListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Isyeri();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.isyeri = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'isyeriListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
