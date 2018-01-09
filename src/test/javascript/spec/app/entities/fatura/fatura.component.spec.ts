/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { FaturaComponent } from '../../../../../../main/webapp/app/entities/fatura/fatura.component';
import { FaturaService } from '../../../../../../main/webapp/app/entities/fatura/fatura.service';
import { Fatura } from '../../../../../../main/webapp/app/entities/fatura/fatura.model';

describe('Component Tests', () => {

    describe('Fatura Management Component', () => {
        let comp: FaturaComponent;
        let fixture: ComponentFixture<FaturaComponent>;
        let service: FaturaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [FaturaComponent],
                providers: [
                    FaturaService
                ]
            })
            .overrideTemplate(FaturaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FaturaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FaturaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Fatura(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.faturas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
