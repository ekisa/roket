/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { KuryeGecmisiComponent } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi.component';
import { KuryeGecmisiService } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi.service';
import { KuryeGecmisi } from '../../../../../../main/webapp/app/entities/kurye-gecmisi/kurye-gecmisi.model';

describe('Component Tests', () => {

    describe('KuryeGecmisi Management Component', () => {
        let comp: KuryeGecmisiComponent;
        let fixture: ComponentFixture<KuryeGecmisiComponent>;
        let service: KuryeGecmisiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [KuryeGecmisiComponent],
                providers: [
                    KuryeGecmisiService
                ]
            })
            .overrideTemplate(KuryeGecmisiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KuryeGecmisiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KuryeGecmisiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new KuryeGecmisi(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.kuryeGecmisis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
