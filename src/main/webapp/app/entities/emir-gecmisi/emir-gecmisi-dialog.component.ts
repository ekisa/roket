import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmirGecmisi } from './emir-gecmisi.model';
import { EmirGecmisiPopupService } from './emir-gecmisi-popup.service';
import { EmirGecmisiService } from './emir-gecmisi.service';
import { Emir, EmirService } from '../emir';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-emir-gecmisi-dialog',
    templateUrl: './emir-gecmisi-dialog.component.html'
})
export class EmirGecmisiDialogComponent implements OnInit {

    emirGecmisi: EmirGecmisi;
    isSaving: boolean;

    emirs: Emir[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private emirGecmisiService: EmirGecmisiService,
        private emirService: EmirService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.emirService.query()
            .subscribe((res: ResponseWrapper) => { this.emirs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.emirGecmisi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.emirGecmisiService.update(this.emirGecmisi));
        } else {
            this.subscribeToSaveResponse(
                this.emirGecmisiService.create(this.emirGecmisi));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmirGecmisi>) {
        result.subscribe((res: EmirGecmisi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EmirGecmisi) {
        this.eventManager.broadcast({ name: 'emirGecmisiListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEmirById(index: number, item: Emir) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-emir-gecmisi-popup',
    template: ''
})
export class EmirGecmisiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emirGecmisiPopupService: EmirGecmisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.emirGecmisiPopupService
                    .open(EmirGecmisiDialogComponent as Component, params['id']);
            } else {
                this.emirGecmisiPopupService
                    .open(EmirGecmisiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
