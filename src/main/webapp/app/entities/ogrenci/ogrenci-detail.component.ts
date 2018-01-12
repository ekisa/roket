import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Ogrenci } from './ogrenci.model';
import { OgrenciService } from './ogrenci.service';

@Component({
    selector: 'jhi-ogrenci-detail',
    templateUrl: './ogrenci-detail.component.html'
})
export class OgrenciDetailComponent implements OnInit, OnDestroy {

    ogrenci: Ogrenci;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ogrenciService: OgrenciService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOgrencis();
    }

    load(id) {
        this.ogrenciService.find(id).subscribe((ogrenci) => {
            this.ogrenci = ogrenci;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOgrencis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ogrenciListModification',
            (response) => this.load(this.ogrenci.id)
        );
    }
}
