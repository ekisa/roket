/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { OgrenciDialogComponent } from '../../../../../../main/webapp/app/entities/ogrenci/ogrenci-dialog.component';
import { OgrenciService } from '../../../../../../main/webapp/app/entities/ogrenci/ogrenci.service';
import { Ogrenci } from '../../../../../../main/webapp/app/entities/ogrenci/ogrenci.model';
import { SinifService } from '../../../../../../main/webapp/app/entities/sinif';

describe('Component Tests', () => {

    describe('Ogrenci Management Dialog Component', () => {
        let comp: OgrenciDialogComponent;
        let fixture: ComponentFixture<OgrenciDialogComponent>;
        let service: OgrenciService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [OgrenciDialogComponent],
                providers: [
                    SinifService,
                    OgrenciService
                ]
            })
            .overrideTemplate(OgrenciDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OgrenciDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OgrenciService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Ogrenci(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.ogrenci = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ogrenciListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Ogrenci();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.ogrenci = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ogrenciListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
