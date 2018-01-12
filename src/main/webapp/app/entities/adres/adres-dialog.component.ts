import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Adres } from './adres.model';
import { AdresPopupService } from './adres-popup.service';
import { AdresService } from './adres.service';
import { Mahalle, MahalleService } from '../mahalle';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-adres-dialog',
    templateUrl: './adres-dialog.component.html'
})
export class AdresDialogComponent implements OnInit {

    adres: Adres;
    isSaving: boolean;

    mahalles: Mahalle[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private adresService: AdresService,
        private mahalleService: MahalleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mahalleService.query()
            .subscribe((res: ResponseWrapper) => { this.mahalles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.adres.id !== undefined) {
            this.subscribeToSaveResponse(
                this.adresService.update(this.adres));
        } else {
            this.subscribeToSaveResponse(
                this.adresService.create(this.adres));
        }
    }

    private subscribeToSaveResponse(result: Observable<Adres>) {
        result.subscribe((res: Adres) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Adres) {
        this.eventManager.broadcast({ name: 'adresListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-adres-popup',
    template: ''
})
export class AdresPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adresPopupService: AdresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.adresPopupService
                    .open(AdresDialogComponent as Component, params['id']);
            } else {
                this.adresPopupService
                    .open(AdresDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
