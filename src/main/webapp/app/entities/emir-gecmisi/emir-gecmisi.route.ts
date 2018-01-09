import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmirGecmisiComponent } from './emir-gecmisi.component';
import { EmirGecmisiDetailComponent } from './emir-gecmisi-detail.component';
import { EmirGecmisiPopupComponent } from './emir-gecmisi-dialog.component';
import { EmirGecmisiDeletePopupComponent } from './emir-gecmisi-delete-dialog.component';

export const emirGecmisiRoute: Routes = [
    {
        path: 'emir-gecmisi',
        component: EmirGecmisiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emirGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'emir-gecmisi/:id',
        component: EmirGecmisiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emirGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emirGecmisiPopupRoute: Routes = [
    {
        path: 'emir-gecmisi-new',
        component: EmirGecmisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emirGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'emir-gecmisi/:id/edit',
        component: EmirGecmisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emirGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'emir-gecmisi/:id/delete',
        component: EmirGecmisiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.emirGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
