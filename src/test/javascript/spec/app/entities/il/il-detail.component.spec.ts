/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { IlDetailComponent } from '../../../../../../main/webapp/app/entities/il/il-detail.component';
import { IlService } from '../../../../../../main/webapp/app/entities/il/il.service';
import { Il } from '../../../../../../main/webapp/app/entities/il/il.model';

describe('Component Tests', () => {

    describe('Il Management Detail Component', () => {
        let comp: IlDetailComponent;
        let fixture: ComponentFixture<IlDetailComponent>;
        let service: IlService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IlDetailComponent],
                providers: [
                    IlService
                ]
            })
            .overrideTemplate(IlDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IlDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IlService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Il(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.il).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
