/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { EmirDetailComponent } from '../../../../../../main/webapp/app/entities/emir/emir-detail.component';
import { EmirService } from '../../../../../../main/webapp/app/entities/emir/emir.service';
import { Emir } from '../../../../../../main/webapp/app/entities/emir/emir.model';

describe('Component Tests', () => {

    describe('Emir Management Detail Component', () => {
        let comp: EmirDetailComponent;
        let fixture: ComponentFixture<EmirDetailComponent>;
        let service: EmirService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [EmirDetailComponent],
                providers: [
                    EmirService
                ]
            })
            .overrideTemplate(EmirDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmirDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmirService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Emir(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.emir).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
