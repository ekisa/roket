import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ogrenci } from './ogrenci.model';
import { OgrenciService } from './ogrenci.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ogrenci',
    templateUrl: './ogrenci.component.html'
})
export class OgrenciComponent implements OnInit, OnDestroy {
ogrencis: Ogrenci[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ogrenciService: OgrenciService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ogrenciService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ogrencis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInOgrencis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Ogrenci) {
        return item.id;
    }
    registerChangeInOgrencis() {
        this.eventSubscriber = this.eventManager.subscribe('ogrenciListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
