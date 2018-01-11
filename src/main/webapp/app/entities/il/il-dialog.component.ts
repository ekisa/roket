import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Il } from './il.model';
import { IlPopupService } from './il-popup.service';
import { IlService } from './il.service';

@Component({
    selector: 'jhi-il-dialog',
    templateUrl: './il-dialog.component.html'
})
export class IlDialogComponent implements OnInit {

    il: Il;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private ilService: IlService,
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
        if (this.il.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ilService.update(this.il));
        } else {
            this.subscribeToSaveResponse(
                this.ilService.create(this.il));
        }
    }

    private subscribeToSaveResponse(result: Observable<Il>) {
        result.subscribe((res: Il) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Il) {
        this.eventManager.broadcast({ name: 'ilListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-il-popup',
    template: ''
})
export class IlPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ilPopupService: IlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ilPopupService
                    .open(IlDialogComponent as Component, params['id']);
            } else {
                this.ilPopupService
                    .open(IlDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
