import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mahalle } from './mahalle.model';
import { MahalleService } from './mahalle.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mahalle',
    templateUrl: './mahalle.component.html'
})
export class MahalleComponent implements OnInit, OnDestroy {
mahalles: Mahalle[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mahalleService: MahalleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mahalleService.query().subscribe(
            (res: ResponseWrapper) => {
                this.mahalles = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMahalles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Mahalle) {
        return item.id;
    }
    registerChangeInMahalles() {
        this.eventSubscriber = this.eventManager.subscribe('mahalleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
