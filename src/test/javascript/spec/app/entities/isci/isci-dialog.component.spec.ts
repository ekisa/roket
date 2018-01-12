/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { IsciDialogComponent } from '../../../../../../main/webapp/app/entities/isci/isci-dialog.component';
import { IsciService } from '../../../../../../main/webapp/app/entities/isci/isci.service';
import { Isci } from '../../../../../../main/webapp/app/entities/isci/isci.model';
import { MotorService } from '../../../../../../main/webapp/app/entities/motor';

describe('Component Tests', () => {

    describe('Isci Management Dialog Component', () => {
        let comp: IsciDialogComponent;
        let fixture: ComponentFixture<IsciDialogComponent>;
        let service: IsciService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IsciDialogComponent],
                providers: [
                    MotorService,
                    IsciService
                ]
            })
            .overrideTemplate(IsciDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IsciDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IsciService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Isci(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.isci = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'isciListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Isci();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.isci = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'isciListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
