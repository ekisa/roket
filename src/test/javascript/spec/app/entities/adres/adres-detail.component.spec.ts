/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { AdresDetailComponent } from '../../../../../../main/webapp/app/entities/adres/adres-detail.component';
import { AdresService } from '../../../../../../main/webapp/app/entities/adres/adres.service';
import { Adres } from '../../../../../../main/webapp/app/entities/adres/adres.model';

describe('Component Tests', () => {

    describe('Adres Management Detail Component', () => {
        let comp: AdresDetailComponent;
        let fixture: ComponentFixture<AdresDetailComponent>;
        let service: AdresService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [AdresDetailComponent],
                providers: [
                    AdresService
                ]
            })
            .overrideTemplate(AdresDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdresDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdresService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Adres(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.adres).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
