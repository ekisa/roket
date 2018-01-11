/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { GPSLokasyonDetailComponent } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon-detail.component';
import { GPSLokasyonService } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon.service';
import { GPSLokasyon } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon.model';

describe('Component Tests', () => {

    describe('GPSLokasyon Management Detail Component', () => {
        let comp: GPSLokasyonDetailComponent;
        let fixture: ComponentFixture<GPSLokasyonDetailComponent>;
        let service: GPSLokasyonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [GPSLokasyonDetailComponent],
                providers: [
                    GPSLokasyonService
                ]
            })
            .overrideTemplate(GPSLokasyonDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GPSLokasyonDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GPSLokasyonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new GPSLokasyon(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.gPSLokasyon).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
