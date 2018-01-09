import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmirComponent } from './emir.component';
import { EmirDetailComponent } from './emir-detail.component';
import { EmirPopupComponent } from './emir-dialog.component';
import { EmirDeletePopupComponent } from './emir-delete-dialog.component';

export const emirRoute: Routes = [
    {
        path: 'emir',
        component: EmirComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emir.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'emir/:id',
        component: EmirDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emir.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emirPopupRoute: Routes = [
    {
        path: 'emir-new',
        component: EmirPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emir.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'emir/:id/edit',
        component: EmirPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emir.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'emir/:id/delete',
        component: EmirDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emir.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
