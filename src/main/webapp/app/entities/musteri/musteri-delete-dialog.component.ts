import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Musteri } from './musteri.model';
import { MusteriPopupService } from './musteri-popup.service';
import { MusteriService } from './musteri.service';

@Component({
    selector: 'jhi-musteri-delete-dialog',
    templateUrl: './musteri-delete-dialog.component.html'
})
export class MusteriDeleteDialogComponent {

    musteri: Musteri;

    constructor(
        private musteriService: MusteriService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.musteriService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'musteriListModification',
                content: 'Deleted an musteri'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-musteri-delete-popup',
    template: ''
})
export class MusteriDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private musteriPopupService: MusteriPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.musteriPopupService
                .open(MusteriDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
