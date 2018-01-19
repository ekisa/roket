import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Kurye } from './kurye.model';
import { KuryePopupService } from './kurye-popup.service';
import { KuryeService } from './kurye.service';
import { Merkez, MerkezService } from '../merkez';
import { GPSLokasyon, GPSLokasyonService } from '../gps-lokasyon';
import { ResponseWrapper } from '../../shared';
import {User} from '../../shared/user/user.model';
import {UserService} from '../../shared/user/user.service';

@Component({
    selector: 'jhi-kurye-dialog',
    templateUrl: './kurye-dialog.component.html'
})
export class KuryeDialogComponent implements OnInit {

    kurye: Kurye;
    isSaving: boolean;

    users: User[];

    merkezs: Merkez[];

    // gpslokasyons: GPSLokasyon[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private kuryeService: KuryeService,
        private userService: UserService,
        private merkezService: MerkezService,
        // private gpsLokasyonService: GPSLokasyonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService
            .query({size: 99999, sort:['login']})
            .subscribe((res: ResponseWrapper) => {
                if (!this.kurye.login) {
                    this.users = res.json;
                } else {
                    this.userService
                        .find(this.kurye.login)
                        .subscribe((subRes: User) => {
                            this.users = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.merkezService.query({sort:['adi'], size:999999})
            .subscribe((res: ResponseWrapper) => { this.merkezs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        /*this.gpsLokasyonService.query()
            .subscribe((res: ResponseWrapper) => { this.gpslokasyons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));*/
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.kurye.id !== undefined) {
            this.subscribeToSaveResponse(
                this.kuryeService.update(this.kurye));
        } else {
            this.subscribeToSaveResponse(
                this.kuryeService.create(this.kurye));
        }
    }

    private subscribeToSaveResponse(result: Observable<Kurye>) {
        result.subscribe((res: Kurye) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Kurye) {
        this.eventManager.broadcast({ name: 'kuryeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.login;
    }

    trackMerkezById(index: number, item: Merkez) {
        return item.id;
    }

    trackGPSLokasyonById(index: number, item: GPSLokasyon) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-kurye-popup',
    template: ''
})
export class KuryePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kuryePopupService: KuryePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.kuryePopupService
                    .open(KuryeDialogComponent as Component, params['id']);
            } else {
                this.kuryePopupService
                    .open(KuryeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
