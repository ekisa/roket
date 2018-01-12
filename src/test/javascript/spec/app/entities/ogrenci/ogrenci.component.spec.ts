/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { OgrenciComponent } from '../../../../../../main/webapp/app/entities/ogrenci/ogrenci.component';
import { OgrenciService } from '../../../../../../main/webapp/app/entities/ogrenci/ogrenci.service';
import { Ogrenci } from '../../../../../../main/webapp/app/entities/ogrenci/ogrenci.model';

describe('Component Tests', () => {

    describe('Ogrenci Management Component', () => {
        let comp: OgrenciComponent;
        let fixture: ComponentFixture<OgrenciComponent>;
        let service: OgrenciService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [OgrenciComponent],
                providers: [
                    OgrenciService
                ]
            })
            .overrideTemplate(OgrenciComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OgrenciComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OgrenciService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Ogrenci(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ogrencis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
