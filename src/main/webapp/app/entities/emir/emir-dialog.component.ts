import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Emir } from './emir.model';
import { EmirPopupService } from './emir-popup.service';
import { EmirService } from './emir.service';
import { Isyeri, IsyeriService } from '../isyeri';
import { Adres, AdresService } from '../adres';
import { GPSLokasyon, GPSLokasyonService } from '../gps-lokasyon';
import { Fatura, FaturaService } from '../fatura';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-emir-dialog',
    templateUrl: './emir-dialog.component.html'
})
export class EmirDialogComponent implements OnInit {

    emir: Emir;
    isSaving: boolean;

    isyeris: Isyeri[];

    /*adres: Adres[];

    gpslokasyons: GPSLokasyon[];

    faturas: Fatura[];*/

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private emirService: EmirService,
        private isyeriService: IsyeriService,
        /*private adresService: AdresService,
        private gPSLokasyonService: GPSLokasyonService,
        private faturaService: FaturaService,*/
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.isyeriService.query({size : 99999, sort: ['adi']})
            .subscribe((res: ResponseWrapper) => { this.isyeris = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        /*this.adresService.query()
            .subscribe((res: ResponseWrapper) => { this.adres = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.gPSLokasyonService.query()
            .subscribe((res: ResponseWrapper) => { this.gpslokasyons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.faturaService.query()
            .subscribe((res: ResponseWrapper) => { this.faturas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));*/
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.emir.id !== undefined) {
            this.subscribeToSaveResponse(
                this.emirService.update(this.emir));
        } else {
            this.subscribeToSaveResponse(
                this.emirService.create(this.emir));
        }
    }

    private subscribeToSaveResponse(result: Observable<Emir>) {
        result.subscribe((res: Emir) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Emir) {
        this.eventManager.broadcast({ name: 'emirListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIsyeriById(index: number, item: Isyeri) {
        return item.id;
    }

    trackAdresById(index: number, item: Adres) {
        return item.id;
    }

    trackGPSLokasyonById(index: number, item: GPSLokasyon) {
        return item.id;
    }

    trackFaturaById(index: number, item: Fatura) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-emir-popup',
    template: ''
})
export class EmirPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emirPopupService: EmirPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.emirPopupService
                    .open(EmirDialogComponent as Component, params['id']);
            } else {
                this.emirPopupService
                    .open(EmirDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
