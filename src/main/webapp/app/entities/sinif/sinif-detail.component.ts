import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Sinif } from './sinif.model';
import { SinifService } from './sinif.service';

@Component({
    selector: 'jhi-sinif-detail',
    templateUrl: './sinif-detail.component.html'
})
export class SinifDetailComponent implements OnInit, OnDestroy {

    sinif: Sinif;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sinifService: SinifService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSinifs();
    }

    load(id) {
        this.sinifService.find(id).subscribe((sinif) => {
            this.sinif = sinif;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSinifs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sinifListModification',
            (response) => this.load(this.sinif.id)
        );
    }
}
