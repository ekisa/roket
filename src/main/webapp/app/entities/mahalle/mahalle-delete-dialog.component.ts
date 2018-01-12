import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mahalle } from './mahalle.model';
import { MahallePopupService } from './mahalle-popup.service';
import { MahalleService } from './mahalle.service';

@Component({
    selector: 'jhi-mahalle-delete-dialog',
    templateUrl: './mahalle-delete-dialog.component.html'
})
export class MahalleDeleteDialogComponent {

    mahalle: Mahalle;

    constructor(
        private mahalleService: MahalleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mahalleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mahalleListModification',
                content: 'Deleted an mahalle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mahalle-delete-popup',
    template: ''
})
export class MahalleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mahallePopupService: MahallePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mahallePopupService
                .open(MahalleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
