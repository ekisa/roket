import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Adres } from './adres.model';
import { AdresService } from './adres.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-adres',
    templateUrl: './adres.component.html'
})
export class AdresComponent implements OnInit, OnDestroy {
adres: Adres[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private adresService: AdresService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.adresService.query().subscribe(
            (res: ResponseWrapper) => {
                this.adres = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAdres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Adres) {
        return item.id;
    }
    registerChangeInAdres() {
        this.eventSubscriber = this.eventManager.subscribe('adresListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
