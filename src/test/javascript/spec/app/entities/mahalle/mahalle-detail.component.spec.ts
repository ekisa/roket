/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { MahalleDetailComponent } from '../../../../../../main/webapp/app/entities/mahalle/mahalle-detail.component';
import { MahalleService } from '../../../../../../main/webapp/app/entities/mahalle/mahalle.service';
import { Mahalle } from '../../../../../../main/webapp/app/entities/mahalle/mahalle.model';

describe('Component Tests', () => {

    describe('Mahalle Management Detail Component', () => {
        let comp: MahalleDetailComponent;
        let fixture: ComponentFixture<MahalleDetailComponent>;
        let service: MahalleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MahalleDetailComponent],
                providers: [
                    MahalleService
                ]
            })
            .overrideTemplate(MahalleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MahalleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MahalleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Mahalle(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mahalle).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
