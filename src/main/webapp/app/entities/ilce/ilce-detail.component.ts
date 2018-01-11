import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Ilce } from './ilce.model';
import { IlceService } from './ilce.service';

@Component({
    selector: 'jhi-ilce-detail',
    templateUrl: './ilce-detail.component.html'
})
export class IlceDetailComponent implements OnInit, OnDestroy {

    ilce: Ilce;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ilceService: IlceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIlces();
    }

    load(id) {
        this.ilceService.find(id).subscribe((ilce) => {
            this.ilce = ilce;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIlces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ilceListModification',
            (response) => this.load(this.ilce.id)
        );
    }
}
