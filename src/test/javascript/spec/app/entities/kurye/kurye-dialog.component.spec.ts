/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { KuryeDialogComponent } from '../../../../../../main/webapp/app/entities/kurye/kurye-dialog.component';
import { KuryeService } from '../../../../../../main/webapp/app/entities/kurye/kurye.service';
import { Kurye } from '../../../../../../main/webapp/app/entities/kurye/kurye.model';
import { MerkezService } from '../../../../../../main/webapp/app/entities/merkez';
import { GPSLokasyonService } from '../../../../../../main/webapp/app/entities/gps-lokasyon';
import {UserService} from '../../../../../../main/webapp/app/shared/user/user.service';

describe('Component Tests', () => {

    describe('Kurye Management Dialog Component', () => {
        let comp: KuryeDialogComponent;
        let fixture: ComponentFixture<KuryeDialogComponent>;
        let service: KuryeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [KuryeDialogComponent],
                providers: [
                    UserService,
                    MerkezService,
                    GPSLokasyonService,
                    KuryeService
                ]
            })
            .overrideTemplate(KuryeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KuryeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KuryeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Kurye(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.kurye = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'kuryeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Kurye();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.kurye = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'kuryeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
