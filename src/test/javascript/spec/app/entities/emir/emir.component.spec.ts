/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { EmirComponent } from '../../../../../../main/webapp/app/entities/emir/emir.component';
import { EmirService } from '../../../../../../main/webapp/app/entities/emir/emir.service';
import { Emir } from '../../../../../../main/webapp/app/entities/emir/emir.model';

describe('Component Tests', () => {

    describe('Emir Management Component', () => {
        let comp: EmirComponent;
        let fixture: ComponentFixture<EmirComponent>;
        let service: EmirService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [EmirComponent],
                providers: [
                    EmirService
                ]
            })
            .overrideTemplate(EmirComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmirComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmirService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Emir(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.emirs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
