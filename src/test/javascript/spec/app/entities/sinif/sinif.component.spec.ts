/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { SinifComponent } from '../../../../../../main/webapp/app/entities/sinif/sinif.component';
import { SinifService } from '../../../../../../main/webapp/app/entities/sinif/sinif.service';
import { Sinif } from '../../../../../../main/webapp/app/entities/sinif/sinif.model';

describe('Component Tests', () => {

    describe('Sinif Management Component', () => {
        let comp: SinifComponent;
        let fixture: ComponentFixture<SinifComponent>;
        let service: SinifService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [SinifComponent],
                providers: [
                    SinifService
                ]
            })
            .overrideTemplate(SinifComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SinifComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SinifService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Sinif(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sinifs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
