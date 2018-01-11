/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { MotorDetailComponent } from '../../../../../../main/webapp/app/entities/motor/motor-detail.component';
import { MotorService } from '../../../../../../main/webapp/app/entities/motor/motor.service';
import { Motor } from '../../../../../../main/webapp/app/entities/motor/motor.model';

describe('Component Tests', () => {

    describe('Motor Management Detail Component', () => {
        let comp: MotorDetailComponent;
        let fixture: ComponentFixture<MotorDetailComponent>;
        let service: MotorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MotorDetailComponent],
                providers: [
                    MotorService
                ]
            })
            .overrideTemplate(MotorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MotorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MotorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Motor(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.motor).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
