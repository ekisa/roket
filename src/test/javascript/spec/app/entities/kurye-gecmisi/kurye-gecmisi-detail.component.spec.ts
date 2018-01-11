/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { KuryeGecmisiDetailComponent } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi-detail.component';
import { KuryeGecmisiService } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi.service';
import { KuryeGecmisi } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi.model';

describe('Component Tests', () => {

    describe('KuryeGecmisi Management Detail Component', () => {
        let comp: KuryeGecmisiDetailComponent;
        let fixture: ComponentFixture<KuryeGecmisiDetailComponent>;
        let service: KuryeGecmisiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [KuryeGecmisiDetailComponent],
                providers: [
                    KuryeGecmisiService
                ]
            })
            .overrideTemplate(KuryeGecmisiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KuryeGecmisiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KuryeGecmisiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new KuryeGecmisi(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.kuryeGecmisi).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
