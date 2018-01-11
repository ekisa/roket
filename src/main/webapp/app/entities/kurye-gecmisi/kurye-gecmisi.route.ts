import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { KuryeGecmisiComponent } from './kurye-gecmisi.component';
import { KuryeGecmisiDetailComponent } from './kurye-gecmisi-detail.component';
import { KuryeGecmisiPopupComponent } from './kurye-gecmisi-dialog.component';
import { KuryeGecmisiDeletePopupComponent } from './kurye-gecmisi-delete-dialog.component';

export const kuryeGecmisiRoute: Routes = [
    {
        path: 'kurye-gecmisi',
        component: KuryeGecmisiComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kuryeGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'kurye-gecmisi/:id',
        component: KuryeGecmisiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kuryeGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kuryeGecmisiPopupRoute: Routes = [
    {
        path: 'kurye-gecmisi-new',
        component: KuryeGecmisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kuryeGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurye-gecmisi/:id/edit',
        component: KuryeGecmisiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kuryeGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kurye-gecmisi/:id/delete',
        component: KuryeGecmisiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'roketApp.kuryeGecmisi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
