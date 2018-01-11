import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    GPSLokasyonService,
    GPSLokasyonPopupService,
    GPSLokasyonComponent,
    GPSLokasyonDetailComponent,
    GPSLokasyonDialogComponent,
    GPSLokasyonPopupComponent,
    GPSLokasyonDeletePopupComponent,
    GPSLokasyonDeleteDialogComponent,
    gPSLokasyonRoute,
    gPSLokasyonPopupRoute,
} from './';

const ENTITY_STATES = [
    ...gPSLokasyonRoute,
    ...gPSLokasyonPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GPSLokasyonComponent,
        GPSLokasyonDetailComponent,
        GPSLokasyonDialogComponent,
        GPSLokasyonDeleteDialogComponent,
        GPSLokasyonPopupComponent,
        GPSLokasyonDeletePopupComponent,
    ],
    entryComponents: [
        GPSLokasyonComponent,
        GPSLokasyonDialogComponent,
        GPSLokasyonPopupComponent,
        GPSLokasyonDeleteDialogComponent,
        GPSLokasyonDeletePopupComponent,
    ],
    providers: [
        GPSLokasyonService,
        GPSLokasyonPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketGPSLokasyonModule {}
