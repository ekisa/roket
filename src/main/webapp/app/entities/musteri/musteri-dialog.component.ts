import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Musteri } from './musteri.model';
import { MusteriPopupService } from './musteri-popup.service';
import { MusteriService } from './musteri.service';

@Component({
    selector: 'jhi-musteri-dialog',
    templateUrl: './musteri-dialog.component.html'
})
export class MusteriDialogComponent implements OnInit {

    musteri: Musteri;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private musteriService: MusteriService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
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
