import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EmirGecmisi } from './emir-gecmisi.model';
import { EmirGecmisiService } from './emir-gecmisi.service';

@Component({
    selector: 'jhi-emir-gecmisi-detail',
    templateUrl: './emir-gecmisi-detail.component.html'
})
export class EmirGecmisiDetailComponent implements OnInit, OnDestroy {

    emirGecmisi: EmirGecmisi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private emirGecmisiService: EmirGecmisiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmirGecmisis();
    }

    load(id) {
        this.emirGecmisiService.find(id).subscribe((emirGecmisi) => {
            this.emirGecmisi = emirGecmisi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmirGecmisis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'emirGecmisiListModification',
            (response) => this.load(this.emirGecmisi.id)
        );
    }
}
