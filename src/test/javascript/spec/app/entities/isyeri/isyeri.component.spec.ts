/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { IsyeriComponent } from '../../../../../../main/webapp/app/entities/isyeri/isyeri.component';
import { IsyeriService } from '../../../../../../main/webapp/app/entities/isyeri/isyeri.service';
import { Isyeri } from '../../../../../../main/webapp/app/entities/isyeri/isyeri.model';

describe('Component Tests', () => {

    describe('Isyeri Management Component', () => {
        let comp: IsyeriComponent;
        let fixture: ComponentFixture<IsyeriComponent>;
        let service: IsyeriService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IsyeriComponent],
                providers: [
                    IsyeriService
                ]
            })
            .overrideTemplate(IsyeriComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IsyeriComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IsyeriService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Isyeri(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.isyeris[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
