/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { RoketTestModule } from '../../../test.module';
import { MerkezDetailComponent } from '../../../../../../main/webapp/app/entities/merkez/merkez-detail.component';
import { MerkezService } from '../../../../../../main/webapp/app/entities/merkez/merkez.service';
import { Merkez } from '../../../../../../main/webapp/app/entities/merkez/merkez.model';

describe('Component Tests', () => {

    describe('Merkez Management Detail Component', () => {
        let comp: MerkezDetailComponent;
        let fixture: ComponentFixture<MerkezDetailComponent>;
        let service: MerkezService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MerkezDetailComponent],
                providers: [
                    MerkezService
                ]
            })
            .overrideTemplate(MerkezDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerkezDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerkezService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Merkez(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.merkez).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
