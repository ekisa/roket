import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ogrenci } from './ogrenci.model';
import { OgrenciPopupService } from './ogrenci-popup.service';
import { OgrenciService } from './ogrenci.service';
import { Sinif, SinifService } from '../sinif';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ogrenci-dialog',
    templateUrl: './ogrenci-dialog.component.html'
})
export class OgrenciDialogComponent implements OnInit {

    ogrenci: Ogrenci;
    isSaving: boolean;

    sinifs: Sinif[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ogrenciService: OgrenciService,
        private sinifService: SinifService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sinifService.query()
            .subscribe((res: ResponseWrapper) => { this.sinifs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ogrenci.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ogrenciService.update(this.ogrenci));
        } else {
            this.subscribeToSaveResponse(
                this.ogrenciService.create(this.ogrenci));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ogrenci>) {
        result.subscribe((res: Ogrenci) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ogrenci) {
        this.eventManager.broadcast({ name: 'ogrenciListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSinifById(index: number, item: Sinif) {
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
    selector: 'jhi-ogrenci-popup',
    template: ''
})
export class OgrenciPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ogrenciPopupService: OgrenciPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ogrenciPopupService
                    .open(OgrenciDialogComponent as Component, params['id']);
            } else {
                this.ogrenciPopupService
                    .open(OgrenciDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
