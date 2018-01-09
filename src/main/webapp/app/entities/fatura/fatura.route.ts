import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FaturaComponent } from './fatura.component';
import { FaturaDetailComponent } from './fatura-detail.component';
import { FaturaPopupComponent } from './fatura-dialog.component';
import { FaturaDeletePopupComponent } from './fatura-delete-dialog.component';

@Injectable()
export class FaturaResolvePagingParams implements Resolve<any> {

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

export const faturaRoute: Routes = [
    {
        path: 'fatura',
        component: FaturaComponent,
        resolve: {
            'pagingParams': FaturaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.fatura.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fatura/:id',
        component: FaturaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.fatura.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const faturaPopupRoute: Routes = [
    {
        path: 'fatura-new',
        component: FaturaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.fatura.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fatura/:id/edit',
        component: FaturaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.fatura.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fatura/:id/delete',
        component: FaturaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.fatura.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
