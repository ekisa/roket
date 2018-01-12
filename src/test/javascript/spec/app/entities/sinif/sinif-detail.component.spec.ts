/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { SinifDetailComponent } from '../../../../../../main/webapp/app/entities/sinif/sinif-detail.component';
import { SinifService } from '../../../../../../main/webapp/app/entities/sinif/sinif.service';
import { Sinif } from '../../../../../../main/webapp/app/entities/sinif/sinif.model';

describe('Component Tests', () => {

    describe('Sinif Management Detail Component', () => {
        let comp: SinifDetailComponent;
        let fixture: ComponentFixture<SinifDetailComponent>;
        let service: SinifService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [SinifDetailComponent],
                providers: [
                    SinifService
                ]
            })
            .overrideTemplate(SinifDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SinifDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SinifService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Sinif(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sinif).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
