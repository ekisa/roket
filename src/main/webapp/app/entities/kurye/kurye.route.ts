import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { KuryeComponent } from './kurye.component';
import { KuryeDetailComponent } from './kurye-detail.component';
import { KuryePopupComponent } from './kurye-dialog.component';
import { KuryeDeletePopupComponent } from './kurye-delete-dialog.component';

@Injectable()
export class KuryeResolvePagingParams implements Resolve<any> {

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

export const kuryeRoute: Routes = [
    {
        path: 'kurye',
        component: KuryeComponent,
        resolve: {
            'pagingParams': KuryeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kurye.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'kurye/:id',
        component: KuryeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kurye.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kuryePopupRoute: Routes = [
    {
        path: 'kurye-new',
        component: KuryePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kurye.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurye/:id/edit',
        component: KuryePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kurye.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurye/:id/delete',
        component: KuryeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kurye.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
