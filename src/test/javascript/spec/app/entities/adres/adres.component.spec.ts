/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { AdresComponent } from '../../../../../../main/webapp/app/entities/adres/adres.component';
import { AdresService } from '../../../../../../main/webapp/app/entities/adres/adres.service';
import { Adres } from '../../../../../../main/webapp/app/entities/adres/adres.model';

describe('Component Tests', () => {

    describe('Adres Management Component', () => {
        let comp: AdresComponent;
        let fixture: ComponentFixture<AdresComponent>;
        let service: AdresService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [AdresComponent],
                providers: [
                    AdresService
                ]
            })
            .overrideTemplate(AdresComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdresComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdresService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Adres(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.adres[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
