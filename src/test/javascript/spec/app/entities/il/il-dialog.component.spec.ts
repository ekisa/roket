/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { IlDialogComponent } from '../../../../../../main/webapp/app/entities/il/il-dialog.component';
import { IlService } from '../../../../../../main/webapp/app/entities/il/il.service';
import { Il } from '../../../../../../main/webapp/app/entities/il/il.model';

describe('Component Tests', () => {

    describe('Il Management Dialog Component', () => {
        let comp: IlDialogComponent;
        let fixture: ComponentFixture<IlDialogComponent>;
        let service: IlService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IlDialogComponent],
                providers: [
                    IlService
                ]
            })
            .overrideTemplate(IlDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IlDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IlService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Il(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.il = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Il();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.il = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ilListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
