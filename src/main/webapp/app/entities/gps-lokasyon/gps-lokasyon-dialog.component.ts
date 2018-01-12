import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GPSLokasyon } from './gps-lokasyon.model';
import { GPSLokasyonPopupService } from './gps-lokasyon-popup.service';
import { GPSLokasyonService } from './gps-lokasyon.service';

@Component({
    selector: 'jhi-gps-lokasyon-dialog',
    templateUrl: './gps-lokasyon-dialog.component.html'
})
export class GPSLokasyonDialogComponent implements OnInit {

    gPSLokasyon: GPSLokasyon;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private gPSLokasyonService: GPSLokasyonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.gPSLokasyon.id !== undefined) {
            this.subscribeToSaveResponse(
                this.gPSLokasyonService.update(this.gPSLokasyon));
        } else {
            this.subscribeToSaveResponse(
                this.gPSLokasyonService.create(this.gPSLokasyon));
        }
    }

    private subscribeToSaveResponse(result: Observable<GPSLokasyon>) {
        result.subscribe((res: GPSLokasyon) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GPSLokasyon) {
        this.eventManager.broadcast({ name: 'gPSLokasyonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-gps-lokasyon-popup',
    template: ''
})
export class GPSLokasyonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gPSLokasyonPopupService: GPSLokasyonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.gPSLokasyonPopupService
                    .open(GPSLokasyonDialogComponent as Component, params['id']);
            } else {
                this.gPSLokasyonPopupService
                    .open(GPSLokasyonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
