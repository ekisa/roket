import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmirGecmisi } from './emir-gecmisi.model';
import { EmirGecmisiPopupService } from './emir-gecmisi-popup.service';
import { EmirGecmisiService } from './emir-gecmisi.service';

@Component({
    selector: 'jhi-emir-gecmisi-delete-dialog',
    templateUrl: './emir-gecmisi-delete-dialog.component.html'
})
export class EmirGecmisiDeleteDialogComponent {

    emirGecmisi: EmirGecmisi;

    constructor(
        private emirGecmisiService: EmirGecmisiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emirGecmisiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'emirGecmisiListModification',
                content: 'Deleted an emirGecmisi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-emir-gecmisi-delete-popup',
    template: ''
})
export class EmirGecmisiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emirGecmisiPopupService: EmirGecmisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.emirGecmisiPopupService
                .open(EmirGecmisiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
