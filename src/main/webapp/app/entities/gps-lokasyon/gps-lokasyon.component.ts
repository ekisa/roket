import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GPSLokasyon } from './gps-lokasyon.model';
import { GPSLokasyonService } from './gps-lokasyon.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-gps-lokasyon',
    templateUrl: './gps-lokasyon.component.html'
})
export class GPSLokasyonComponent implements OnInit, OnDestroy {
gPSLokasyons: GPSLokasyon[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private gPSLokasyonService: GPSLokasyonService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.gPSLokasyonService.query().subscribe(
            (res: ResponseWrapper) => {
                this.gPSLokasyons = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGPSLokasyons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: GPSLokasyon) {
        return item.id;
    }
    registerChangeInGPSLokasyons() {
        this.eventSubscriber = this.eventManager.subscribe('gPSLokasyonListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
