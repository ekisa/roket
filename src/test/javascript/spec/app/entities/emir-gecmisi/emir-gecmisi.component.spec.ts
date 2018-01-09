/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { EmirGecmisiComponent } from '../../../../../../main/webapp/app/entities/emir-gecmisi/emir-gecmisi.component';
import { EmirGecmisiService } from '../../../../../../main/webapp/app/entities/emir-gecmisi/emir-gecmisi.service';
import { EmirGecmisi } from '../../../../../../main/webapp/app/entities/emir-gecmisi/emir-gecmisi.model';

describe('Component Tests', () => {

    describe('EmirGecmisi Management Component', () => {
        let comp: EmirGecmisiComponent;
        let fixture: ComponentFixture<EmirGecmisiComponent>;
        let service: EmirGecmisiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [EmirGecmisiComponent],
                providers: [
                    EmirGecmisiService
                ]
            })
            .overrideTemplate(EmirGecmisiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmirGecmisiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmirGecmisiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new EmirGecmisi(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.emirGecmisis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
