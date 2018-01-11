import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    MotorService,
    MotorPopupService,
    MotorComponent,
    MotorDetailComponent,
    MotorDialogComponent,
    MotorPopupComponent,
    MotorDeletePopupComponent,
    MotorDeleteDialogComponent,
    motorRoute,
    motorPopupRoute,
    MotorResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...motorRoute,
    ...motorPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MotorComponent,
        MotorDetailComponent,
        MotorDialogComponent,
        MotorDeleteDialogComponent,
        MotorPopupComponent,
        MotorDeletePopupComponent,
    ],
    entryComponents: [
        MotorComponent,
        MotorDialogComponent,
        MotorPopupComponent,
        MotorDeleteDialogComponent,
        MotorDeletePopupComponent,
    ],
    providers: [
        MotorService,
        MotorPopupService,
        MotorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketMotorModule {}
