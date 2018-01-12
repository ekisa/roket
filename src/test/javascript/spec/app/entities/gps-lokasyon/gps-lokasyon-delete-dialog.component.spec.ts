/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { GPSLokasyonDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon-delete-dialog.component';
import { GPSLokasyonService } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon.service';

describe('Component Tests', () => {

    describe('GPSLokasyon Management Delete Component', () => {
        let comp: GPSLokasyonDeleteDialogComponent;
        let fixture: ComponentFixture<GPSLokasyonDeleteDialogComponent>;
        let service: GPSLokasyonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [GPSLokasyonDeleteDialogComponent],
                providers: [
                    GPSLokasyonService
                ]
            })
            .overrideTemplate(GPSLokasyonDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GPSLokasyonDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GPSLokasyonService);
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
