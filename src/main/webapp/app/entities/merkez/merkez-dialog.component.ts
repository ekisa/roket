import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Merkez } from './merkez.model';
import { MerkezPopupService } from './merkez-popup.service';
import { MerkezService } from './merkez.service';
import { Mahalle, MahalleService } from '../mahalle';
import { GPSLokasyon, GPSLokasyonService } from '../gps-lokasyon';
import { Adres, AdresService } from '../adres';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-merkez-dialog',
    templateUrl: './merkez-dialog.component.html'
})
export class MerkezDialogComponent implements OnInit {

    merkez: Merkez;
    isSaving: boolean;

    mahalles: Mahalle[];

    /*gpslokasyons: GPSLokasyon[];

    adres: Adres[];*/

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private merkezService: MerkezService,
        private mahalleService: MahalleService,
        /*private gPSLokasyonService: GPSLokasyonService,
        private adresService: AdresService,*/
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mahalleService.query({size: 99999, sort:['adi']})
            .subscribe((res: ResponseWrapper) => { this.mahalles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
       /* this.gPSLokasyonService.query()
            .subscribe((res: ResponseWrapper) => { this.gpslokasyons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.adresService.query()
            .subscribe((res: ResponseWrapper) => { this.adres = res.json; }, (res: ResponseWrapper) => this.onError(res.json));*/
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.merkez.id !== undefined) {
            this.subscribeToSaveResponse(
                this.merkezService.update(this.merkez));
        } else {
            this.subscribeToSaveResponse(
                this.merkezService.create(this.merkez));
        }
    }

    private subscribeToSaveResponse(result: Observable<Merkez>) {
        result.subscribe((res: Merkez) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Merkez) {
        this.eventManager.broadcast({ name: 'merkezListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMahalleById(index: number, item: Mahalle) {
        return item.id;
    }

    trackGPSLokasyonById(index: number, item: GPSLokasyon) {
        return item.id;
    }

    trackAdresById(index: number, item: Adres) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-merkez-popup',
    template: ''
})
export class MerkezPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merkezPopupService: MerkezPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.merkezPopupService
                    .open(MerkezDialogComponent as Component, params['id']);
            } else {
                this.merkezPopupService
                    .open(MerkezDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
