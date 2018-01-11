import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MotorComponent } from './motor.component';
import { MotorDetailComponent } from './motor-detail.component';
import { MotorPopupComponent } from './motor-dialog.component';
import { MotorDeletePopupComponent } from './motor-delete-dialog.component';

@Injectable()
export class MotorResolvePagingParams implements Resolve<any> {

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

export const motorRoute: Routes = [
    {
        path: 'motor',
        component: MotorComponent,
        resolve: {
            'pagingParams': MotorResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.motor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'motor/:id',
        component: MotorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.motor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const motorPopupRoute: Routes = [
    {
        path: 'motor-new',
        component: MotorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.motor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'motor/:id/edit',
        component: MotorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.motor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'motor/:id/delete',
        component: MotorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.motor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
