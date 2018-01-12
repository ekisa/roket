/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { MusteriDetailComponent } from '../../../../../../main/webapp/app/entities/musteri/musteri-detail.component';
import { MusteriService } from '../../../../../../main/webapp/app/entities/musteri/musteri.service';
import { Musteri } from '../../../../../../main/webapp/app/entities/musteri/musteri.model';

describe('Component Tests', () => {

    describe('Musteri Management Detail Component', () => {
        let comp: MusteriDetailComponent;
        let fixture: ComponentFixture<MusteriDetailComponent>;
        let service: MusteriService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MusteriDetailComponent],
                providers: [
                    MusteriService
                ]
            })
            .overrideTemplate(MusteriDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MusteriDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MusteriService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Musteri(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.musteri).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
