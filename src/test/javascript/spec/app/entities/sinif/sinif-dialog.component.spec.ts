/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { SinifDialogComponent } from '../../../../../../main/webapp/app/entities/sinif/sinif-dialog.component';
import { SinifService } from '../../../../../../main/webapp/app/entities/sinif/sinif.service';
import { Sinif } from '../../../../../../main/webapp/app/entities/sinif/sinif.model';
import { OgrenciService } from '../../../../../../main/webapp/app/entities/ogrenci';

describe('Component Tests', () => {

    describe('Sinif Management Dialog Component', () => {
        let comp: SinifDialogComponent;
        let fixture: ComponentFixture<SinifDialogComponent>;
        let service: SinifService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [SinifDialogComponent],
                providers: [
                    OgrenciService,
                    SinifService
                ]
            })
            .overrideTemplate(SinifDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SinifDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SinifService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Sinif(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.sinif = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sinifListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Sinif();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.sinif = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sinifListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
