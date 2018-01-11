import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MusteriComponent } from './musteri.component';
import { MusteriDetailComponent } from './musteri-detail.component';
import { MusteriPopupComponent } from './musteri-dialog.component';
import { MusteriDeletePopupComponent } from './musteri-delete-dialog.component';

@Injectable()
export class MusteriResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const musteriRoute: Routes = [
    {
        path: 'musteri',
        component: MusteriComponent,
        resolve: {
            'pagingParams': MusteriResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'musteri/:id',
        component: MusteriDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const musteriPopupRoute: Routes = [
    {
        path: 'musteri-new',
        component: MusteriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'musteri/:id/edit',
        component: MusteriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'musteri/:id/delete',
        component: MusteriDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
