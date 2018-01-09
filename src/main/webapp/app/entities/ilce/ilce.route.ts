import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { IlceComponent } from './ilce.component';
import { IlceDetailComponent } from './ilce-detail.component';
import { IlcePopupComponent } from './ilce-dialog.component';
import { IlceDeletePopupComponent } from './ilce-delete-dialog.component';

export const ilceRoute: Routes = [
    {
        path: 'ilce',
        component: IlceComponent,
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
