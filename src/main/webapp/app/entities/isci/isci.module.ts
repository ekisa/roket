import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    IsciService,
    IsciPopupService,
    IsciComponent,
    IsciDetailComponent,
    IsciDialogComponent,
    IsciPopupComponent,
    IsciDeletePopupComponent,
    IsciDeleteDialogComponent,
    isciRoute,
    isciPopupRoute,
    IsciResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...isciRoute,
    ...isciPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IsciComponent,
        IsciDetailComponent,
        IsciDialogComponent,
        IsciDeleteDialogComponent,
        IsciPopupComponent,
        IsciDeletePopupComponent,
    ],
    entryComponents: [
        IsciComponent,
        IsciDialogComponent,
        IsciPopupComponent,
        IsciDeleteDialogComponent,
        IsciDeletePopupComponent,
    ],
    providers: [
        IsciService,
        IsciPopupService,
        IsciResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketIsciModule {}
