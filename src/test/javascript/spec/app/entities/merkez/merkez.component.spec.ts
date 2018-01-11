/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { RoketTestModule } from '../../../test.module';
import { MerkezComponent } from '../../../../../../main/webapp/app/entities/merkez/merkez.component';
import { MerkezService } from '../../../../../../main/webapp/app/entities/merkez/merkez.service';
import { Merkez } from '../../../../../../main/webapp/app/entities/merkez/merkez.model';

describe('Component Tests', () => {

    describe('Merkez Management Component', () => {
        let comp: MerkezComponent;
        let fixture: ComponentFixture<MerkezComponent>;
        let service: MerkezService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RoketTestModule],
                declarations: [MerkezComponent],
                providers: [
                    MerkezService
                ]
            })
            .overrideTemplate(MerkezComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerkezComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerkezService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Merkez(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.merkezs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
