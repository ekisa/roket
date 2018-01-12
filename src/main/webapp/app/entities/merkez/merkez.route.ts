import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MerkezComponent } from './merkez.component';
import { MerkezDetailComponent } from './merkez-detail.component';
import { MerkezPopupComponent } from './merkez-dialog.component';
import { MerkezDeletePopupComponent } from './merkez-delete-dialog.component';

@Injectable()
export class MerkezResolvePagingParams implements Resolve<any> {

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

export const merkezRoute: Routes = [
    {
        path: 'merkez',
        component: MerkezComponent,
        resolve: {
            'pagingParams': MerkezResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.merkez.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'merkez/:id',
        component: MerkezDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.merkez.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const merkezPopupRoute: Routes = [
    {
        path: 'merkez-new',
        component: MerkezPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.merkez.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'merkez/:id/edit',
        component: MerkezPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.merkez.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'merkez/:id/delete',
        component: MerkezDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.merkez.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
