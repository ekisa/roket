import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KuryeGecmisi } from './kurye-gecmisi.model';
import { KuryeGecmisiPopupService } from './kurye-gecmisi-popup.service';
import { KuryeGecmisiService } from './kurye-gecmisi.service';

@Component({
    selector: 'jhi-kurye-gecmisi-delete-dialog',
    templateUrl: './kurye-gecmisi-delete-dialog.component.html'
})
export class KuryeGecmisiDeleteDialogComponent {

    kuryeGecmisi: KuryeGecmisi;

    constructor(
        private kuryeGecmisiService: KuryeGecmisiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kuryeGecmisiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kuryeGecmisiListModification',
                content: 'Deleted an kuryeGecmisi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kurye-gecmisi-delete-popup',
    template: ''
})
export class KuryeGecmisiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kuryeGecmisiPopupService: KuryeGecmisiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kuryeGecmisiPopupService
                .open(KuryeGecmisiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
