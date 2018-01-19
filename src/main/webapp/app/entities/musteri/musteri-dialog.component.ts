import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Musteri } from './musteri.model';
import { MusteriPopupService } from './musteri-popup.service';
import { MusteriService } from './musteri.service';
import { Adres, AdresService } from '../adres';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-musteri-dialog',
    templateUrl: './musteri-dialog.component.html'
})
export class MusteriDialogComponent implements OnInit {

    musteri: Musteri;
    isSaving: boolean;

    adres: Adres[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private musteriService: MusteriService,
        private adresService: AdresService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        /*this.adresService
            .query({filter: 'musteri-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.musteri.adresId) {
                    this.adres = res.json;
                } else {
                    this.adresService
                        .find(this.musteri.adresId)
                        .subscribe((subRes: Adres) => {
                            this.adres = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));*/
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.musteri.id !== undefined) {
            this.subscribeToSaveResponse(
                this.musteriService.update(this.musteri));
        } else {
            this.subscribeToSaveResponse(
                this.musteriService.create(this.musteri));
        }
    }

    private subscribeToSaveResponse(result: Observable<Musteri>) {
        result.subscribe((res: Musteri) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Musteri) {
        this.eventManager.broadcast({ name: 'musteriListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAdresById(index: number, item: Adres) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-musteri-popup',
    template: ''
})
export class MusteriPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private musteriPopupService: MusteriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.musteriPopupService
                    .open(MusteriDialogComponent as Component, params['id']);
            } else {
                this.musteriPopupService
                    .open(MusteriDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
