/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { MahalleDialogComponent } from '../../../../../../main/webapp/app/entities/mahalle/mahalle-dialog.component';
import { MahalleService } from '../../../../../../main/webapp/app/entities/mahalle/mahalle.service';
import { Mahalle } from '../../../../../../main/webapp/app/entities/mahalle/mahalle.model';

describe('Component Tests', () => {

    describe('Mahalle Management Dialog Component', () => {
        let comp: MahalleDialogComponent;
        let fixture: ComponentFixture<MahalleDialogComponent>;
        let service: MahalleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MahalleDialogComponent],
                providers: [
                    MahalleService
                ]
            })
            .overrideTemplate(MahalleDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MahalleDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MahalleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Mahalle(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.mahalle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mahalleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Mahalle();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.mahalle = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mahalleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
