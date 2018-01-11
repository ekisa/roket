import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AdresComponent } from './adres.component';
import { AdresDetailComponent } from './adres-detail.component';
import { AdresPopupComponent } from './adres-dialog.component';
import { AdresDeletePopupComponent } from './adres-delete-dialog.component';

@Injectable()
export class AdresResolvePagingParams implements Resolve<any> {

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

export const adresRoute: Routes = [
    {
        path: 'adres',
        component: AdresComponent,
        resolve: {
            'pagingParams': AdresResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'adres/:id',
        component: AdresDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adresPopupRoute: Routes = [
    {
        path: 'adres-new',
        component: AdresPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'adres/:id/edit',
        component: AdresPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'adres/:id/delete',
        component: AdresDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
