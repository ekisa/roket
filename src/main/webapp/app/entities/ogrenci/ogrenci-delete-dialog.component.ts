import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ogrenci } from './ogrenci.model';
import { OgrenciPopupService } from './ogrenci-popup.service';
import { OgrenciService } from './ogrenci.service';

@Component({
    selector: 'jhi-ogrenci-delete-dialog',
    templateUrl: './ogrenci-delete-dialog.component.html'
})
export class OgrenciDeleteDialogComponent {

    ogrenci: Ogrenci;

    constructor(
        private ogrenciService: OgrenciService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ogrenciService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ogrenciListModification',
                content: 'Deleted an ogrenci'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ogrenci-delete-popup',
    template: ''
})
export class OgrenciDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ogrenciPopupService: OgrenciPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ogrenciPopupService
                .open(OgrenciDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
