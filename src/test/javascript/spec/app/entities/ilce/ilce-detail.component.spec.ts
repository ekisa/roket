/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { IlceDetailComponent } from '../../../../../../main/webapp/app/entities/ilce/ilce-detail.component';
import { IlceService } from '../../../../../../main/webapp/app/entities/ilce/ilce.service';
import { Ilce } from '../../../../../../main/webapp/app/entities/ilce/ilce.model';

describe('Component Tests', () => {

    describe('Ilce Management Detail Component', () => {
        let comp: IlceDetailComponent;
        let fixture: ComponentFixture<IlceDetailComponent>;
        let service: IlceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IlceDetailComponent],
                providers: [
                    IlceService
                ]
            })
            .overrideTemplate(IlceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IlceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IlceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Ilce(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ilce).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
