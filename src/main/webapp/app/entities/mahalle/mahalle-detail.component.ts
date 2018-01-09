import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Mahalle } from './mahalle.model';
import { MahalleService } from './mahalle.service';

@Component({
    selector: 'jhi-mahalle-detail',
    templateUrl: './mahalle-detail.component.html'
})
export class MahalleDetailComponent implements OnInit, OnDestroy {

    mahalle: Mahalle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mahalleService: MahalleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMahalles();
    }

    load(id) {
        this.mahalleService.find(id).subscribe((mahalle) => {
            this.mahalle = mahalle;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMahalles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mahalleListModification',
            (response) => this.load(this.mahalle.id)
        );
    }
}
