import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GPSLokasyonComponent } from './gps-lokasyon.component';
import { GPSLokasyonDetailComponent } from './gps-lokasyon-detail.component';
import { GPSLokasyonPopupComponent } from './gps-lokasyon-dialog.component';
import { GPSLokasyonDeletePopupComponent } from './gps-lokasyon-delete-dialog.component';

export const gPSLokasyonRoute: Routes = [
    {
        path: 'gps-lokasyon',
        component: GPSLokasyonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.gPSLokasyon.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gps-lokasyon/:id',
        component: GPSLokasyonDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.gPSLokasyon.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gPSLokasyonPopupRoute: Routes = [
    {
        path: 'gps-lokasyon-new',
        component: GPSLokasyonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.gPSLokasyon.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gps-lokasyon/:id/edit',
        component: GPSLokasyonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.gPSLokasyon.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gps-lokasyon/:id/delete',
        component: GPSLokasyonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.gPSLokasyon.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
