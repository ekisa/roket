/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { EmirGecmisiDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/emir-gecmisi/emir-gecmisi-delete-dialog.component';
import { EmirGecmisiService } from '../../../../../../main/webapp/app/entities/emir-gecmisi/emir-gecmisi.service';

describe('Component Tests', () => {

    describe('EmirGecmisi Management Delete Component', () => {
        let comp: EmirGecmisiDeleteDialogComponent;
        let fixture: ComponentFixture<EmirGecmisiDeleteDialogComponent>;
        let service: EmirGecmisiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [EmirGecmisiDeleteDialogComponent],
                providers: [
                    EmirGecmisiService
                ]
            })
            .overrideTemplate(EmirGecmisiDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmirGecmisiDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmirGecmisiService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
