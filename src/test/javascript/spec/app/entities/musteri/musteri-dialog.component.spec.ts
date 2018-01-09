/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { MusteriDialogComponent } from '../../../../../../main/webapp/app/entities/musteri/musteri-dialog.component';
import { MusteriService } from '../../../../../../main/webapp/app/entities/musteri/musteri.service';
import { Musteri } from '../../../../../../main/webapp/app/entities/musteri/musteri.model';
import { AdresService } from '../../../../../../main/webapp/app/entities/adres';

describe('Component Tests', () => {

    describe('Musteri Management Dialog Component', () => {
        let comp: MusteriDialogComponent;
        let fixture: ComponentFixture<MusteriDialogComponent>;
        let service: MusteriService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MusteriDialogComponent],
                providers: [
                    AdresService,
                    MusteriService
                ]
            })
            .overrideTemplate(MusteriDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MusteriDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MusteriService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Musteri(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.musteri = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'musteriListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Musteri();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.musteri = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'musteriListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
