import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Isci } from './isci.model';
import { IsciPopupService } from './isci-popup.service';
import { IsciService } from './isci.service';
import { Motor, MotorService } from '../motor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-isci-dialog',
    templateUrl: './isci-dialog.component.html'
})
export class IsciDialogComponent implements OnInit {

    isci: Isci;
    isSaving: boolean;

    motors: Motor[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private isciService: IsciService,
        private motorService: MotorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.motorService.query()
            .subscribe((res: ResponseWrapper) => { this.motors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.isci.id !== undefined) {
            this.subscribeToSaveResponse(
                this.isciService.update(this.isci));
        } else {
            this.subscribeToSaveResponse(
                this.isciService.create(this.isci));
        }
    }

    private subscribeToSaveResponse(result: Observable<Isci>) {
        result.subscribe((res: Isci) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Isci) {
        this.eventManager.broadcast({ name: 'isciListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMotorById(index: number, item: Motor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-isci-popup',
    template: ''
})
export class IsciPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private isciPopupService: IsciPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.isciPopupService
                    .open(IsciDialogComponent as Component, params['id']);
            } else {
                this.isciPopupService
                    .open(IsciDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
