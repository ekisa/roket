import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Isyeri } from './isyeri.model';
import { IsyeriService } from './isyeri.service';

@Component({
    selector: 'jhi-isyeri-detail',
    templateUrl: './isyeri-detail.component.html'
})
export class IsyeriDetailComponent implements OnInit, OnDestroy {

    isyeri: Isyeri;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private isyeriService: IsyeriService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIsyeris();
    }

    load(id) {
        this.isyeriService.find(id).subscribe((isyeri) => {
            this.isyeri = isyeri;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIsyeris() {
        this.eventSubscriber = this.eventManager.subscribe(
            'isyeriListModification',
            (response) => this.load(this.isyeri.id)
        );
    }
}
