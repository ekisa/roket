/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { EmirGecmisiDetailComponent } from '../../../../../../main/webapp/app/entities/emir-gecmisi/emir-gecmisi-detail.component';
import { EmirGecmisiService } from '../../../../../../main/webapp/app/entities/emir-gecmisi/emir-gecmisi.service';
import { EmirGecmisi } from '../../../../../../main/webapp/app/entities/emir-gecmisi/emir-gecmisi.model';

describe('Component Tests', () => {

    describe('EmirGecmisi Management Detail Component', () => {
        let comp: EmirGecmisiDetailComponent;
        let fixture: ComponentFixture<EmirGecmisiDetailComponent>;
        let service: EmirGecmisiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [EmirGecmisiDetailComponent],
                providers: [
                    EmirGecmisiService
                ]
            })
            .overrideTemplate(EmirGecmisiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmirGecmisiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmirGecmisiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EmirGecmisi(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.emirGecmisi).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
