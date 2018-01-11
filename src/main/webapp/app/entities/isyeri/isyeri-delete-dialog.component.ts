import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Isyeri } from './isyeri.model';
import { IsyeriPopupService } from './isyeri-popup.service';
import { IsyeriService } from './isyeri.service';

@Component({
    selector: 'jhi-isyeri-delete-dialog',
    templateUrl: './isyeri-delete-dialog.component.html'
})
export class IsyeriDeleteDialogComponent {

    isyeri: Isyeri;

    constructor(
        private isyeriService: IsyeriService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.isyeriService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'isyeriListModification',
                content: 'Deleted an isyeri'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-isyeri-delete-popup',
    template: ''
})
export class IsyeriDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private isyeriPopupService: IsyeriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.isyeriPopupService
                .open(IsyeriDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
