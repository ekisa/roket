import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IsciComponent } from './isci.component';
import { IsciDetailComponent } from './isci-detail.component';
import { IsciPopupComponent } from './isci-dialog.component';
import { IsciDeletePopupComponent } from './isci-delete-dialog.component';

@Injectable()
export class IsciResolvePagingParams implements Resolve<any> {

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

export const isciRoute: Routes = [
    {
        path: 'isci',
        component: IsciComponent,
        resolve: {
            'pagingParams': IsciResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isci.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'isci/:id',
        component: IsciDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isci.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const isciPopupRoute: Routes = [
    {
        path: 'isci-new',
        component: IsciPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isci.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'isci/:id/edit',
        component: IsciPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isci.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'isci/:id/delete',
        component: IsciDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isci.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
