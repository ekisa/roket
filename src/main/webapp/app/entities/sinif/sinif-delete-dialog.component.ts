import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Sinif } from './sinif.model';
import { SinifPopupService } from './sinif-popup.service';
import { SinifService } from './sinif.service';

@Component({
    selector: 'jhi-sinif-delete-dialog',
    templateUrl: './sinif-delete-dialog.component.html'
})
export class SinifDeleteDialogComponent {

    sinif: Sinif;

    constructor(
        private sinifService: SinifService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sinifService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sinifListModification',
                content: 'Deleted an sinif'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sinif-delete-popup',
    template: ''
})
export class SinifDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sinifPopupService: SinifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sinifPopupService
                .open(SinifDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
