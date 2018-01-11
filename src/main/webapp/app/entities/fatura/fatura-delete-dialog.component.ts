import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Fatura } from './fatura.model';
import { FaturaPopupService } from './fatura-popup.service';
import { FaturaService } from './fatura.service';

@Component({
    selector: 'jhi-fatura-delete-dialog',
    templateUrl: './fatura-delete-dialog.component.html'
})
export class FaturaDeleteDialogComponent {

    fatura: Fatura;

    constructor(
        private faturaService: FaturaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.faturaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'faturaListModification',
                content: 'Deleted an fatura'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fatura-delete-popup',
    template: ''
})
export class FaturaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private faturaPopupService: FaturaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.faturaPopupService
                .open(FaturaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
