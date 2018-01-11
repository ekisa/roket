/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { IlceComponent } from '../../../../../../main/webapp/app/entities/ilce/ilce.component';
import { IlceService } from '../../../../../../main/webapp/app/entities/ilce/ilce.service';
import { Ilce } from '../../../../../../main/webapp/app/entities/ilce/ilce.model';

describe('Component Tests', () => {

    describe('Ilce Management Component', () => {
        let comp: IlceComponent;
        let fixture: ComponentFixture<IlceComponent>;
        let service: IlceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IlceComponent],
                providers: [
                    IlceService
                ]
            })
            .overrideTemplate(IlceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IlceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IlceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Ilce(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ilces[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
