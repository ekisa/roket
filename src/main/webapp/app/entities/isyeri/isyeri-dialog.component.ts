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
import { Adres, AdresService } from '../adres';
import { GPSLokasyon, GPSLokasyonService } from '../gps-lokasyon';
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

    adres: Adres[];

    gpslokasyons: GPSLokasyon[];

    musteris: Musteri[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private isyeriService: IsyeriService,
        private merkezService: MerkezService,
        private adresService: AdresService,
        private gPSLokasyonService: GPSLokasyonService,
        private musteriService: MusteriService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.merkezService
            .query({filter: 'isyeri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.isyeri.merkezId) {
                    this.merkezs = res.json;
                } else {
                    this.merkezService
                        .find(this.isyeri.merkezId)
                        .subscribe((subRes: Merkez) => {
                            this.merkezs = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.adresService
            .query({filter: 'isyeri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.isyeri.adresId) {
                    this.adres = res.json;
                } else {
                    this.adresService
                        .find(this.isyeri.adresId)
                        .subscribe((subRes: Adres) => {
                            this.adres = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.gPSLokasyonService
            .query({filter: 'isyeri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.isyeri.gpsLokasyonId) {
                    this.gpslokasyons = res.json;
                } else {
                    this.gPSLokasyonService
                        .find(this.isyeri.gpsLokasyonId)
                        .subscribe((subRes: GPSLokasyon) => {
                            this.gpslokasyons = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
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

    trackAdresById(index: number, item: Adres) {
        return item.id;
    }

    trackGPSLokasyonById(index: number, item: GPSLokasyon) {
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
