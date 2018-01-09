/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { KuryeComponent } from '../../../../../../main/webapp/app/entities/kurye/kurye.component';
import { KuryeService } from '../../../../../../main/webapp/app/entities/kurye/kurye.service';
import { Kurye } from '../../../../../../main/webapp/app/entities/kurye/kurye.model';

describe('Component Tests', () => {

    describe('Kurye Management Component', () => {
        let comp: KuryeComponent;
        let fixture: ComponentFixture<KuryeComponent>;
        let service: KuryeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [KuryeComponent],
                providers: [
                    KuryeService
                ]
            })
            .overrideTemplate(KuryeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KuryeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KuryeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Kurye(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.kuryes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
