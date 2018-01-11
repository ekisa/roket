import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Kurye } from './kurye.model';
import { KuryeService } from './kurye.service';

@Component({
    selector: 'jhi-kurye-detail',
    templateUrl: './kurye-detail.component.html'
})
export class KuryeDetailComponent implements OnInit, OnDestroy {

    kurye: Kurye;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kuryeService: KuryeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKuryes();
    }

    load(id) {
        this.kuryeService.find(id).subscribe((kurye) => {
            this.kurye = kurye;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKuryes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kuryeListModification',
            (response) => this.load(this.kurye.id)
        );
    }
}
