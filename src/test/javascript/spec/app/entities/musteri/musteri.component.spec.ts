/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { MusteriComponent } from '../../../../../../main/webapp/app/entities/musteri/musteri.component';
import { MusteriService } from '../../../../../../main/webapp/app/entities/musteri/musteri.service';
import { Musteri } from '../../../../../../main/webapp/app/entities/musteri/musteri.model';

describe('Component Tests', () => {

    describe('Musteri Management Component', () => {
        let comp: MusteriComponent;
        let fixture: ComponentFixture<MusteriComponent>;
        let service: MusteriService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MusteriComponent],
                providers: [
                    MusteriService
                ]
            })
            .overrideTemplate(MusteriComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MusteriComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MusteriService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Musteri(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.musteris[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
