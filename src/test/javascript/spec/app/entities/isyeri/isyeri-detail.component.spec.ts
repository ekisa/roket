/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { IsyeriDetailComponent } from '../../../../../../main/webapp/app/entities/isyeri/isyeri-detail.component';
import { IsyeriService } from '../../../../../../main/webapp/app/entities/isyeri/isyeri.service';
import { Isyeri } from '../../../../../../main/webapp/app/entities/isyeri/isyeri.model';

describe('Component Tests', () => {

    describe('Isyeri Management Detail Component', () => {
        let comp: IsyeriDetailComponent;
        let fixture: ComponentFixture<IsyeriDetailComponent>;
        let service: IsyeriService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IsyeriDetailComponent],
                providers: [
                    IsyeriService
                ]
            })
            .overrideTemplate(IsyeriDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IsyeriDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IsyeriService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Isyeri(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.isyeri).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
