import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ilce } from './ilce.model';
import { IlcePopupService } from './ilce-popup.service';
import { IlceService } from './ilce.service';
import { Il, IlService } from '../il';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ilce-dialog',
    templateUrl: './ilce-dialog.component.html'
})
export class IlceDialogComponent implements OnInit {

    ilce: Ilce;
    isSaving: boolean;

    ils: Il[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ilceService: IlceService,
        private ilService: IlService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ilService
            .query({filter: 'ilce-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.ilce.ilId) {
                    this.ils = res.json;
                } else {
                    this.ilService
                        .find(this.ilce.ilId)
                        .subscribe((subRes: Il) => {
                            this.ils = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ilce.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ilceService.update(this.ilce));
        } else {
            this.subscribeToSaveResponse(
                this.ilceService.create(this.ilce));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ilce>) {
        result.subscribe((res: Ilce) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ilce) {
        this.eventManager.broadcast({ name: 'ilceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIlById(index: number, item: Il) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ilce-popup',
    template: ''
})
export class IlcePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ilcePopupService: IlcePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ilcePopupService
                    .open(IlceDialogComponent as Component, params['id']);
            } else {
                this.ilcePopupService
                    .open(IlceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
