import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Emir } from './emir.model';
import { EmirService } from './emir.service';

@Component({
    selector: 'jhi-emir-detail',
    templateUrl: './emir-detail.component.html'
})
export class EmirDetailComponent implements OnInit, OnDestroy {

    emir: Emir;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private emirService: EmirService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmirs();
    }

    load(id) {
        this.emirService.find(id).subscribe((emir) => {
            this.emir = emir;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmirs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'emirListModification',
            (response) => this.load(this.emir.id)
        );
    }
}
