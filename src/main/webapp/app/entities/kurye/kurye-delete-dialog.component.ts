import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Kurye } from './kurye.model';
import { KuryePopupService } from './kurye-popup.service';
import { KuryeService } from './kurye.service';

@Component({
    selector: 'jhi-kurye-delete-dialog',
    templateUrl: './kurye-delete-dialog.component.html'
})
export class KuryeDeleteDialogComponent {

    kurye: Kurye;

    constructor(
        private kuryeService: KuryeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kuryeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kuryeListModification',
                content: 'Deleted an kurye'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kurye-delete-popup',
    template: ''
})
export class KuryeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kuryePopupService: KuryePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kuryePopupService
                .open(KuryeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
