/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { KuryeGecmisiDialogComponent } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi-dialog.component';
import { KuryeGecmisiService } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi.service';
import { KuryeGecmisi } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi.model';
import { KuryeService } from '../../../../../../main/webapp/app/entities/kurye';

describe('Component Tests', () => {

    describe('KuryeGecmisi Management Dialog Component', () => {
        let comp: KuryeGecmisiDialogComponent;
        let fixture: ComponentFixture<KuryeGecmisiDialogComponent>;
        let service: KuryeGecmisiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [KuryeGecmisiDialogComponent],
                providers: [
                    KuryeService,
                    KuryeGecmisiService
                ]
            })
            .overrideTemplate(KuryeGecmisiDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KuryeGecmisiDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KuryeGecmisiService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new KuryeGecmisi(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.kuryeGecmisi = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'kuryeGecmisiListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new KuryeGecmisi();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.kuryeGecmisi = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'kuryeGecmisiListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
