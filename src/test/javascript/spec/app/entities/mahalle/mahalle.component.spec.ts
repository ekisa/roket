/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { MahalleComponent } from '../../../../../../main/webapp/app/entities/mahalle/mahalle.component';
import { MahalleService } from '../../../../../../main/webapp/app/entities/mahalle/mahalle.service';
import { Mahalle } from '../../../../../../main/webapp/app/entities/mahalle/mahalle.model';

describe('Component Tests', () => {

    describe('Mahalle Management Component', () => {
        let comp: MahalleComponent;
        let fixture: ComponentFixture<MahalleComponent>;
        let service: MahalleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MahalleComponent],
                providers: [
                    MahalleService
                ]
            })
            .overrideTemplate(MahalleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MahalleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MahalleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Mahalle(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mahalles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
