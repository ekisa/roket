import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IlceComponent } from './ilce.component';
import { IlceDetailComponent } from './ilce-detail.component';
import { IlcePopupComponent } from './ilce-dialog.component';
import { IlceDeletePopupComponent } from './ilce-delete-dialog.component';

@Injectable()
export class IlceResolvePagingParams implements Resolve<any> {

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

export const ilceRoute: Routes = [
    {
        path: 'ilce',
        component: IlceComponent,
        resolve: {
            'pagingParams': IlceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ilce.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ilce/:id',
        component: IlceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ilce.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ilcePopupRoute: Routes = [
    {
        path: 'ilce-new',
        component: IlcePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ilce.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ilce/:id/edit',
        component: IlcePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ilce.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ilce/:id/delete',
        component: IlceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ilce.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
