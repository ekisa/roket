import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Isci } from './isci.model';
import { IsciPopupService } from './isci-popup.service';
import { IsciService } from './isci.service';

@Component({
    selector: 'jhi-isci-delete-dialog',
    templateUrl: './isci-delete-dialog.component.html'
})
export class IsciDeleteDialogComponent {

    isci: Isci;

    constructor(
        private isciService: IsciService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.isciService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'isciListModification',
                content: 'Deleted an isci'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-isci-delete-popup',
    template: ''
})
export class IsciDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private isciPopupService: IsciPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.isciPopupService
                .open(IsciDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
