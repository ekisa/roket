import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Emir } from './emir.model';
import { EmirPopupService } from './emir-popup.service';
import { EmirService } from './emir.service';

@Component({
    selector: 'jhi-emir-delete-dialog',
    templateUrl: './emir-delete-dialog.component.html'
})
export class EmirDeleteDialogComponent {

    emir: Emir;

    constructor(
        private emirService: EmirService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emirService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'emirListModification',
                content: 'Deleted an emir'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-emir-delete-popup',
    template: ''
})
export class EmirDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emirPopupService: EmirPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.emirPopupService
                .open(EmirDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
