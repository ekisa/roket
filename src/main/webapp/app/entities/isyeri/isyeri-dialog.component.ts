import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Isyeri } from './isyeri.model';
import { IsyeriPopupService } from './isyeri-popup.service';
import { IsyeriService } from './isyeri.service';
import { Merkez, MerkezService } from '../merkez';
import { GPSLokasyon, GPSLokasyonService } from '../gps-lokasyon';
import { Adres, AdresService } from '../adres';
import { Musteri, MusteriService } from '../musteri';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-isyeri-dialog',
    templateUrl: './isyeri-dialog.component.html'
})
export class IsyeriDialogComponent implements OnInit {

    isyeri: Isyeri;
    isSaving: boolean;

    merkezs: Merkez[];

    gpslokasyons: GPSLokasyon[];

    adres: Adres[];

    musteris: Musteri[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private isyeriService: IsyeriService,
        private merkezService: MerkezService,
        private gPSLokasyonService: GPSLokasyonService,
        private adresService: AdresService,
        private musteriService: MusteriService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.merkezService.query()
            .subscribe((res: ResponseWrapper) => { this.merkezs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.gPSLokasyonService.query()
            .subscribe((res: ResponseWrapper) => { this.gpslokasyons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.adresService.query()
            .subscribe((res: ResponseWrapper) => { this.adres = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.musteriService.query()
            .subscribe((res: ResponseWrapper) => { this.musteris = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.isyeri.id !== undefined) {
            this.subscribeToSaveResponse(
                this.isyeriService.update(this.isyeri));
        } else {
            this.subscribeToSaveResponse(
                this.isyeriService.create(this.isyeri));
        }
    }

    private subscribeToSaveResponse(result: Observable<Isyeri>) {
        result.subscribe((res: Isyeri) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Isyeri) {
        this.eventManager.broadcast({ name: 'isyeriListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMerkezById(index: number, item: Merkez) {
        return item.id;
    }

    trackGPSLokasyonById(index: number, item: GPSLokasyon) {
        return item.id;
    }

    trackAdresById(index: number, item: Adres) {
        return item.id;
    }

    trackMusteriById(index: number, item: Musteri) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-isyeri-popup',
    template: ''
})
export class IsyeriPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private isyeriPopupService: IsyeriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.isyeriPopupService
                    .open(IsyeriDialogComponent as Component, params['id']);
            } else {
                this.isyeriPopupService
                    .open(IsyeriDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}