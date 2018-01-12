import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Sinif } from './sinif.model';
import { SinifService } from './sinif.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sinif',
    templateUrl: './sinif.component.html'
})
export class SinifComponent implements OnInit, OnDestroy {
sinifs: Sinif[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sinifService: SinifService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sinifService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sinifs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSinifs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Sinif) {
        return item.id;
    }
    registerChangeInSinifs() {
        this.eventSubscriber = this.eventManager.subscribe('sinifListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
