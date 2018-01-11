/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { IlComponent } from '../../../../../../main/webapp/app/entities/il/il.component';
import { IlService } from '../../../../../../main/webapp/app/entities/il/il.service';
import { Il } from '../../../../../../main/webapp/app/entities/il/il.model';

describe('Component Tests', () => {

    describe('Il Management Component', () => {
        let comp: IlComponent;
        let fixture: ComponentFixture<IlComponent>;
        let service: IlService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IlComponent],
                providers: [
                    IlService
                ]
            })
            .overrideTemplate(IlComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IlComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IlService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Il(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ils[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
