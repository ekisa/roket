/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { KuryeDetailComponent } from '../../../../../../main/webapp/app/entities/kurye/kurye-detail.component';
import { KuryeService } from '../../../../../../main/webapp/app/entities/kurye/kurye.service';
import { Kurye } from '../../../../../../main/webapp/app/entities/kurye/kurye.model';

describe('Component Tests', () => {

    describe('Kurye Management Detail Component', () => {
        let comp: KuryeDetailComponent;
        let fixture: ComponentFixture<KuryeDetailComponent>;
        let service: KuryeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [KuryeDetailComponent],
                providers: [
                    KuryeService
                ]
            })
            .overrideTemplate(KuryeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KuryeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KuryeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Kurye(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.kurye).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
