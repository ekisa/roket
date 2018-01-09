import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Musteri } from './musteri.model';
import { MusteriService } from './musteri.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-musteri',
    templateUrl: './musteri.component.html'
})
export class MusteriComponent implements OnInit, OnDestroy {
musteris: Musteri[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private musteriService: MusteriService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.musteriService.query().subscribe(
            (res: ResponseWrapper) => {
                this.musteris = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMusteris();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Musteri) {
        return item.id;
    }
    registerChangeInMusteris() {
        this.eventSubscriber = this.eventManager.subscribe('musteriListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
