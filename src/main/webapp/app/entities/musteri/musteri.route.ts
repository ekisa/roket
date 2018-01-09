import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MusteriComponent } from './musteri.component';
import { MusteriDetailComponent } from './musteri-detail.component';
import { MusteriPopupComponent } from './musteri-dialog.component';
import { MusteriDeletePopupComponent } from './musteri-delete-dialog.component';

export const musteriRoute: Routes = [
    {
        path: 'musteri',
        component: MusteriComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'musteri/:id',
        component: MusteriDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const musteriPopupRoute: Routes = [
    {
        path: 'musteri-new',
        component: MusteriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'musteri/:id/edit',
        component: MusteriPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'musteri/:id/delete',
        component: MusteriDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.musteri.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
