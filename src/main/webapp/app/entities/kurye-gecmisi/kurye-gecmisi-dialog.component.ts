import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KuryeGecmisi } from './kurye-gecmisi.model';
import { KuryeGecmisiPopupService } from './kurye-gecmisi-popup.service';
import { KuryeGecmisiService } from './kurye-gecmisi.service';
import { Kurye, KuryeService } from '../kurye';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-kurye-gecmisi-dialog',
    templateUrl: './kurye-gecmisi-dialog.component.html'
})
export class KuryeGecmisiDialogComponent implements OnInit {

    kuryeGecmisi: KuryeGecmisi;
    isSaving: boolean;

    kuryes: Kurye[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private kuryeGecmisiService: KuryeGecmisiService,
        private kuryeService: KuryeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.kuryeService.query()
            .subscribe((res: ResponseWrapper) => { this.kuryes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.kuryeGecmisi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kuryeGecmisiService.update(this.kuryeGecmisi));
        } else {
            this.subscribeToSaveResponse(
                this.kuryeGecmisiService.create(this.kuryeGecmisi));
        }
    }

    private subscribeToSaveResponse(result: Observable<KuryeGecmisi>) {
        result.subscribe((res: KuryeGecmisi) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: KuryeGecmisi) {
        this.eventManager.broadcast({ name: 'kuryeGecmisiListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackKuryeById(index: number, item: Kurye) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-kurye-gecmisi-popup',
    template: ''
})
export class KuryeGecmisiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kuryeGecmisiPopupService: KuryeGecmisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kuryeGecmisiPopupService
                    .open(KuryeGecmisiDialogComponent as Component, params['id']);
            } else {
                this.kuryeGecmisiPopupService
                    .open(KuryeGecmisiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
