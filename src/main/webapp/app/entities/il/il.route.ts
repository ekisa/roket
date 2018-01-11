import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IlComponent } from './il.component';
import { IlDetailComponent } from './il-detail.component';
import { IlPopupComponent } from './il-dialog.component';
import { IlDeletePopupComponent } from './il-delete-dialog.component';

@Injectable()
export class IlResolvePagingParams implements Resolve<any> {

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

export const ilRoute: Routes = [
    {
        path: 'il',
        component: IlComponent,
        resolve: {
            'pagingParams': IlResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.il.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'il/:id',
        component: IlDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.il.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ilPopupRoute: Routes = [
    {
        path: 'il-new',
        component: IlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.il.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'il/:id/edit',
        component: IlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.il.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'il/:id/delete',
        component: IlDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.il.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
