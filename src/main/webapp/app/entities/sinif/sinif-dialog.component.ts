import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Sinif } from './sinif.model';
import { SinifPopupService } from './sinif-popup.service';
import { SinifService } from './sinif.service';
import { Ogrenci, OgrenciService } from '../ogrenci';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sinif-dialog',
    templateUrl: './sinif-dialog.component.html'
})
export class SinifDialogComponent implements OnInit {

    sinif: Sinif;
    isSaving: boolean;

    ogrencis: Ogrenci[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sinifService: SinifService,
        private ogrenciService: OgrenciService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ogrenciService.query()
            .subscribe((res: ResponseWrapper) => { this.ogrencis = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sinif.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sinifService.update(this.sinif));
        } else {
            this.subscribeToSaveResponse(
                this.sinifService.create(this.sinif));
        }
    }

    private subscribeToSaveResponse(result: Observable<Sinif>) {
        result.subscribe((res: Sinif) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Sinif) {
        this.eventManager.broadcast({ name: 'sinifListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOgrenciById(index: number, item: Ogrenci) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-sinif-popup',
    template: ''
})
export class SinifPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sinifPopupService: SinifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sinifPopupService
                    .open(SinifDialogComponent as Component, params['id']);
            } else {
                this.sinifPopupService
                    .open(SinifDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
