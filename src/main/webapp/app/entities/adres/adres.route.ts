import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AdresComponent } from './adres.component';
import { AdresDetailComponent } from './adres-detail.component';
import { AdresPopupComponent } from './adres-dialog.component';
import { AdresDeletePopupComponent } from './adres-delete-dialog.component';

export const adresRoute: Routes = [
    {
        path: 'adres',
        component: AdresComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'adres/:id',
        component: AdresDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adresPopupRoute: Routes = [
    {
        path: 'adres-new',
        component: AdresPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'adres/:id/edit',
        component: AdresPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'adres/:id/delete',
        component: AdresDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.adres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
