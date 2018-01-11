import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Musteri } from './musteri.model';
import { MusteriService } from './musteri.service';

@Component({
    selector: 'jhi-musteri-detail',
    templateUrl: './musteri-detail.component.html'
})
export class MusteriDetailComponent implements OnInit, OnDestroy {

    musteri: Musteri;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private musteriService: MusteriService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMusteris();
    }

    load(id) {
        this.musteriService.find(id).subscribe((musteri) => {
            this.musteri = musteri;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMusteris() {
        this.eventSubscriber = this.eventManager.subscribe(
            'musteriListModification',
            (response) => this.load(this.musteri.id)
        );
    }
}
