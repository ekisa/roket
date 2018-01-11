import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GPSLokasyon } from './gps-lokasyon.model';
import { GPSLokasyonPopupService } from './gps-lokasyon-popup.service';
import { GPSLokasyonService } from './gps-lokasyon.service';

@Component({
    selector: 'jhi-gps-lokasyon-delete-dialog',
    templateUrl: './gps-lokasyon-delete-dialog.component.html'
})
export class GPSLokasyonDeleteDialogComponent {

    gPSLokasyon: GPSLokasyon;

    constructor(
        private gPSLokasyonService: GPSLokasyonService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gPSLokasyonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'gPSLokasyonListModification',
                content: 'Deleted an gPSLokasyon'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gps-lokasyon-delete-popup',
    template: ''
})
export class GPSLokasyonDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gPSLokasyonPopupService: GPSLokasyonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.gPSLokasyonPopupService
                .open(GPSLokasyonDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
