import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { KuryeGecmisi } from './kurye-gecmisi.model';
import { KuryeGecmisiService } from './kurye-gecmisi.service';

@Component({
    selector: 'jhi-kurye-gecmisi-detail',
    templateUrl: './kurye-gecmisi-detail.component.html'
})
export class KuryeGecmisiDetailComponent implements OnInit, OnDestroy {

    kuryeGecmisi: KuryeGecmisi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kuryeGecmisiService: KuryeGecmisiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKuryeGecmisis();
    }

    load(id) {
        this.kuryeGecmisiService.find(id).subscribe((kuryeGecmisi) => {
            this.kuryeGecmisi = kuryeGecmisi;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKuryeGecmisis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kuryeGecmisiListModification',
            (response) => this.load(this.kuryeGecmisi.id)
        );
    }
}
