import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ilce } from './ilce.model';
import { IlceService } from './ilce.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ilce',
    templateUrl: './ilce.component.html'
})
export class IlceComponent implements OnInit, OnDestroy {
ilces: Ilce[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ilceService: IlceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ilceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ilces = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIlces();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Ilce) {
        return item.id;
    }
    registerChangeInIlces() {
        this.eventSubscriber = this.eventManager.subscribe('ilceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
