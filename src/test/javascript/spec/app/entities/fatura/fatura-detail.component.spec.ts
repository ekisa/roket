/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { FaturaDetailComponent } from '../../../../../../main/webapp/app/entities/fatura/fatura-detail.component';
import { FaturaService } from '../../../../../../main/webapp/app/entities/fatura/fatura.service';
import { Fatura } from '../../../../../../main/webapp/app/entities/fatura/fatura.model';

describe('Component Tests', () => {

    describe('Fatura Management Detail Component', () => {
        let comp: FaturaDetailComponent;
        let fixture: ComponentFixture<FaturaDetailComponent>;
        let service: FaturaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [FaturaDetailComponent],
                providers: [
                    FaturaService
                ]
            })
            .overrideTemplate(FaturaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FaturaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaturaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Fatura(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.fatura).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
