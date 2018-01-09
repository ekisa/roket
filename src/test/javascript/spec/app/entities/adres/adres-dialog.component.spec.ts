/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { RoketTestModule } from '../../../test.module';
import { AdresDialogComponent } from '../../../../../../main/webapp/app/entities/adres/adres-dialog.component';
import { AdresService } from '../../../../../../main/webapp/app/entities/adres/adres.service';
import { Adres } from '../../../../../../main/webapp/app/entities/adres/adres.model';
import { MahalleService } from '../../../../../../main/webapp/app/entities/mahalle';
import { IlceService } from '../../../../../../main/webapp/app/entities/ilce';

describe('Component Tests', () => {

    describe('Adres Management Dialog Component', () => {
        let comp: AdresDialogComponent;
        let fixture: ComponentFixture<AdresDialogComponent>;
        let service: AdresService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [AdresDialogComponent],
                providers: [
                    MahalleService,
                    IlceService,
                    AdresService
                ]
            })
            .overrideTemplate(AdresDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdresDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdresService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Adres(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.adres = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'adresListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Adres();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.adres = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'adresListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
