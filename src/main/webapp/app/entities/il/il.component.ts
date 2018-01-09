import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Il } from './il.model';
import { IlService } from './il.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-il',
    templateUrl: './il.component.html'
})
export class IlComponent implements OnInit, OnDestroy {
ils: Il[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ilService: IlService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ilService.query().subscribe(
            (res: ResponseWrapper) => {
                this.ils = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIls();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Il) {
        return item.id;
    }
    registerChangeInIls() {
        this.eventSubscriber = this.eventManager.subscribe('ilListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
