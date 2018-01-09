/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { GPSLokasyonComponent } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon.component';
import { GPSLokasyonService } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon.service';
import { GPSLokasyon } from '../../../../../../main/webapp/app/entities/gps-lokasyon/gps-lokasyon.model';

describe('Component Tests', () => {

    describe('GPSLokasyon Management Component', () => {
        let comp: GPSLokasyonComponent;
        let fixture: ComponentFixture<GPSLokasyonComponent>;
        let service: GPSLokasyonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [GPSLokasyonComponent],
                providers: [
                    GPSLokasyonService
                ]
            })
            .overrideTemplate(GPSLokasyonComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GPSLokasyonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GPSLokasyonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new GPSLokasyon(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.gPSLokasyons[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
