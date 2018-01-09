import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MahalleComponent } from './mahalle.component';
import { MahalleDetailComponent } from './mahalle-detail.component';
import { MahallePopupComponent } from './mahalle-dialog.component';
import { MahalleDeletePopupComponent } from './mahalle-delete-dialog.component';

export const mahalleRoute: Routes = [
    {
        path: 'mahalle',
        component: MahalleComponent,
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
