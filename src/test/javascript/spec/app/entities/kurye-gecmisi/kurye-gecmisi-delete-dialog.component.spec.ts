/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { KuryeGecmisiDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi-delete-dialog.component';
import { KuryeGecmisiService } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi.service';

describe('Component Tests', () => {

    describe('KuryeGecmisi Management Delete Component', () => {
        let comp: KuryeGecmisiDeleteDialogComponent;
        let fixture: ComponentFixture<KuryeGecmisiDeleteDialogComponent>;
        let service: KuryeGecmisiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [KuryeGecmisiDeleteDialogComponent],
                providers: [
                    KuryeGecmisiService
                ]
            })
            .overrideTemplate(KuryeGecmisiDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KuryeGecmisiDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KuryeGecmisiService);
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
