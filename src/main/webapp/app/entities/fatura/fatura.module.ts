import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    FaturaService,
    FaturaPopupService,
    FaturaComponent,
    FaturaDetailComponent,
    FaturaDialogComponent,
    FaturaPopupComponent,
    FaturaDeletePopupComponent,
    FaturaDeleteDialogComponent,
    faturaRoute,
    faturaPopupRoute,
    FaturaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...faturaRoute,
    ...faturaPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FaturaComponent,
        FaturaDetailComponent,
        FaturaDialogComponent,
        FaturaDeleteDialogComponent,
        FaturaPopupComponent,
        FaturaDeletePopupComponent,
    ],
    entryComponents: [
        FaturaComponent,
        FaturaDialogComponent,
        FaturaPopupComponent,
        FaturaDeleteDialogComponent,
        FaturaDeletePopupComponent,
    ],
    providers: [
        FaturaService,
        FaturaPopupService,
        FaturaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketFaturaModule {}
