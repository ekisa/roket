/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { OgrenciDetailComponent } from '../../../../../../main/webapp/app/entities/ogrenci/ogrenci-detail.component';
import { OgrenciService } from '../../../../../../main/webapp/app/entities/ogrenci/ogrenci.service';
import { Ogrenci } from '../../../../../../main/webapp/app/entities/ogrenci/ogrenci.model';

describe('Component Tests', () => {

    describe('Ogrenci Management Detail Component', () => {
        let comp: OgrenciDetailComponent;
        let fixture: ComponentFixture<OgrenciDetailComponent>;
        let service: OgrenciService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [OgrenciDetailComponent],
                providers: [
                    OgrenciService
                ]
            })
            .overrideTemplate(OgrenciDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OgrenciDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OgrenciService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Ogrenci(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ogrenci).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
