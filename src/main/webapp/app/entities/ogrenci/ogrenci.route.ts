import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OgrenciComponent } from './ogrenci.component';
import { OgrenciDetailComponent } from './ogrenci-detail.component';
import { OgrenciPopupComponent } from './ogrenci-dialog.component';
import { OgrenciDeletePopupComponent } from './ogrenci-delete-dialog.component';

export const ogrenciRoute: Routes = [
    {
        path: 'ogrenci',
        component: OgrenciComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ogrenci.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ogrenci/:id',
        component: OgrenciDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ogrenci.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ogrenciPopupRoute: Routes = [
    {
        path: 'ogrenci-new',
        component: OgrenciPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ogrenci.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ogrenci/:id/edit',
        component: OgrenciPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ogrenci.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ogrenci/:id/delete',
        component: OgrenciDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.ogrenci.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
