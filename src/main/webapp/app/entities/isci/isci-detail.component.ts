import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Isci } from './isci.model';
import { IsciService } from './isci.service';

@Component({
    selector: 'jhi-isci-detail',
    templateUrl: './isci-detail.component.html'
})
export class IsciDetailComponent implements OnInit, OnDestroy {

    isci: Isci;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private isciService: IsciService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIscis();
    }

    load(id) {
        this.isciService.find(id).subscribe((isci) => {
            this.isci = isci;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIscis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'isciListModification',
            (response) => this.load(this.isci.id)
        );
    }
}
