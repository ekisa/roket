import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Merkez } from './merkez.model';
import { MerkezPopupService } from './merkez-popup.service';
import { MerkezService } from './merkez.service';

@Component({
    selector: 'jhi-merkez-delete-dialog',
    templateUrl: './merkez-delete-dialog.component.html'
})
export class MerkezDeleteDialogComponent {

    merkez: Merkez;

    constructor(
        private merkezService: MerkezService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.merkezService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'merkezListModification',
                content: 'Deleted an merkez'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-merkez-delete-popup',
    template: ''
})
export class MerkezDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merkezPopupService: MerkezPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.merkezPopupService
                .open(MerkezDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
