import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Fatura } from './fatura.model';
import { FaturaPopupService } from './fatura-popup.service';
import { FaturaService } from './fatura.service';
import { Musteri, MusteriService } from '../musteri';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-fatura-dialog',
    templateUrl: './fatura-dialog.component.html'
})
export class FaturaDialogComponent implements OnInit {

    fatura: Fatura;
    isSaving: boolean;

    musteris: Musteri[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private faturaService: FaturaService,
        private musteriService: MusteriService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.musteriService.query()
            .subscribe((res: ResponseWrapper) => { this.musteris = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fatura.id !== undefined) {
            this.subscribeToSaveResponse(
                this.faturaService.update(this.fatura));
        } else {
            this.subscribeToSaveResponse(
                this.faturaService.create(this.fatura));
        }
    }

    private subscribeToSaveResponse(result: Observable<Fatura>) {
        result.subscribe((res: Fatura) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Fatura) {
        this.eventManager.broadcast({ name: 'faturaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMusteriById(index: number, item: Musteri) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-fatura-popup',
    template: ''
})
export class FaturaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private faturaPopupService: FaturaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.faturaPopupService
                    .open(FaturaDialogComponent as Component, params['id']);
            } else {
                this.faturaPopupService
                    .open(FaturaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
