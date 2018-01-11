import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Merkez } from './merkez.model';
import { MerkezService } from './merkez.service';

@Component({
    selector: 'jhi-merkez-detail',
    templateUrl: './merkez-detail.component.html'
})
export class MerkezDetailComponent implements OnInit, OnDestroy {

    merkez: Merkez;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private merkezService: MerkezService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMerkezs();
    }

    load(id) {
        this.merkezService.find(id).subscribe((merkez) => {
            this.merkez = merkez;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMerkezs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'merkezListModification',
            (response) => this.load(this.merkez.id)
        );
    }
}
