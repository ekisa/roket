/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { IsciDetailComponent } from '../../../../../../main/webapp/app/entities/isci/isci-detail.component';
import { IsciService } from '../../../../../../main/webapp/app/entities/isci/isci.service';
import { Isci } from '../../../../../../main/webapp/app/entities/isci/isci.model';

describe('Component Tests', () => {

    describe('Isci Management Detail Component', () => {
        let comp: IsciDetailComponent;
        let fixture: ComponentFixture<IsciDetailComponent>;
        let service: IsciService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [IsciDetailComponent],
                providers: [
                    IsciService
                ]
            })
            .overrideTemplate(IsciDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IsciDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IsciService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Isci(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.isci).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
