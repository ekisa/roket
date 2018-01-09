/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { IlceDialogComponent } from '../../../../../../main/webapp/app/entities/ilce/ilce-dialog.component';
import { IlceService } from '../../../../../../main/webapp/app/entities/ilce/ilce.service';
import { Ilce } from '../../../../../../main/webapp/app/entities/ilce/ilce.model';
import { IlService } from '../../../../../../main/webapp/app/entities/il';

describe('Component Tests', () => {

    describe('Ilce Management Dialog Component', () => {
        let comp: IlceDialogComponent;
        let fixture: ComponentFixture<IlceDialogComponent>;
        let service: IlceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IlceDialogComponent],
                providers: [
                    IlService,
                    IlceService
                ]
            })
            .overrideTemplate(IlceDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IlceDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IlceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Ilce(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.ilce = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ilceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Ilce();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.ilce = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'ilceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
