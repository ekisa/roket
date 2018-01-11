import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { GPSLokasyon } from './gps-lokasyon.model';
import { GPSLokasyonService } from './gps-lokasyon.service';

@Component({
    selector: 'jhi-gps-lokasyon-detail',
    templateUrl: './gps-lokasyon-detail.component.html'
})
export class GPSLokasyonDetailComponent implements OnInit, OnDestroy {

    gPSLokasyon: GPSLokasyon;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private gPSLokasyonService: GPSLokasyonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGPSLokasyons();
    }

    load(id) {
        this.gPSLokasyonService.find(id).subscribe((gPSLokasyon) => {
            this.gPSLokasyon = gPSLokasyon;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGPSLokasyons() {
        this.eventSubscriber = this.eventManager.subscribe(
            'gPSLokasyonListModification',
            (response) => this.load(this.gPSLokasyon.id)
        );
    }
}
