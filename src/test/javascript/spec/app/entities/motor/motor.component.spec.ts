/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { MotorComponent } from '../../../../../../main/webapp/app/entities/motor/motor.component';
import { MotorService } from '../../../../../../main/webapp/app/entities/motor/motor.service';
import { Motor } from '../../../../../../main/webapp/app/entities/motor/motor.model';

describe('Component Tests', () => {

    describe('Motor Management Component', () => {
        let comp: MotorComponent;
        let fixture: ComponentFixture<MotorComponent>;
        let service: MotorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MotorComponent],
                providers: [
                    MotorService
                ]
            })
            .overrideTemplate(MotorComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MotorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MotorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Motor(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.motors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
