import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IsyeriComponent } from './isyeri.component';
import { IsyeriDetailComponent } from './isyeri-detail.component';
import { IsyeriPopupComponent } from './isyeri-dialog.component';
import { IsyeriDeletePopupComponent } from './isyeri-delete-dialog.component';
import {UserMgmtDetailComponent} from '../../admin/user-management/user-management-detail.component';

@Injectable()
export class IsyeriResolvePagingParams implements Resolve<any> {

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

export const isyeriRoute: Routes = [
    {
        path: 'isyeri',
        component: IsyeriComponent,
        resolve: {
            'pagingParams': IsyeriResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isyeri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'isyeri/:id',
        component: IsyeriDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isyeri.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-management/:login',
        component: UserMgmtDetailComponent,
        data: {
            pageTitle: 'userManagement.home.title'
        }
    }
];

export const isyeriPopupRoute: Routes = [
    {
        path: 'isyeri-new',
        component: IsyeriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isyeri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'isyeri/:id/edit',
        component: IsyeriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isyeri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'isyeri/:id/delete',
        component: IsyeriDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.isyeri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
