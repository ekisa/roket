import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MahalleComponent } from './mahalle.component';
import { MahalleDetailComponent } from './mahalle-detail.component';
import { MahallePopupComponent } from './mahalle-dialog.component';
import { MahalleDeletePopupComponent } from './mahalle-delete-dialog.component';

@Injectable()
export class MahalleResolvePagingParams implements Resolve<any> {

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

export const mahalleRoute: Routes = [
    {
        path: 'mahalle',
        component: MahalleComponent,
        resolve: {
            'pagingParams': MahalleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.mahalle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mahalle/:id',
        component: MahalleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.mahalle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mahallePopupRoute: Routes = [
    {
        path: 'mahalle-new',
        component: MahallePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.mahalle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mahalle/:id/edit',
        component: MahallePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.mahalle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mahalle/:id/delete',
        component: MahalleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.mahalle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
