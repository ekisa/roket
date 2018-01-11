import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Fatura } from './fatura.model';
import { FaturaService } from './fatura.service';

@Component({
    selector: 'jhi-fatura-detail',
    templateUrl: './fatura-detail.component.html'
})
export class FaturaDetailComponent implements OnInit, OnDestroy {

    fatura: Fatura;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private faturaService: FaturaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFaturas();
    }

    load(id) {
        this.faturaService.find(id).subscribe((fatura) => {
            this.fatura = fatura;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFaturas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'faturaListModification',
            (response) => this.load(this.fatura.id)
        );
    }
}
