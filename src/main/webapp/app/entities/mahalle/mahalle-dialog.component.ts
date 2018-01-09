import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mahalle } from './mahalle.model';
import { MahallePopupService } from './mahalle-popup.service';
import { MahalleService } from './mahalle.service';

@Component({
    selector: 'jhi-mahalle-dialog',
    templateUrl: './mahalle-dialog.component.html'
})
export class MahalleDialogComponent implements OnInit {

    mahalle: Mahalle;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private mahalleService: MahalleService,
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
        if (this.mahalle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mahalleService.update(this.mahalle));
        } else {
            this.subscribeToSaveResponse(
                this.mahalleService.create(this.mahalle));
        }
    }

    private subscribeToSaveResponse(result: Observable<Mahalle>) {
        result.subscribe((res: Mahalle) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Mahalle) {
        this.eventManager.broadcast({ name: 'mahalleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-mahalle-popup',
    template: ''
})
export class MahallePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mahallePopupService: MahallePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mahallePopupService
                    .open(MahalleDialogComponent as Component, params['id']);
            } else {
                this.mahallePopupService
                    .open(MahalleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
