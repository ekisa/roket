import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ilce } from './ilce.model';
import { IlcePopupService } from './ilce-popup.service';
import { IlceService } from './ilce.service';

@Component({
    selector: 'jhi-ilce-delete-dialog',
    templateUrl: './ilce-delete-dialog.component.html'
})
export class IlceDeleteDialogComponent {

    ilce: Ilce;

    constructor(
        private ilceService: IlceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ilceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ilceListModification',
                content: 'Deleted an ilce'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ilce-delete-popup',
    template: ''
})
export class IlceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ilcePopupService: IlcePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ilcePopupService
                .open(IlceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
