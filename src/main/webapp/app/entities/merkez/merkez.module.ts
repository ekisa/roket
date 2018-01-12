import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    MerkezService,
    MerkezPopupService,
    MerkezComponent,
    MerkezDetailComponent,
    MerkezDialogComponent,
    MerkezPopupComponent,
    MerkezDeletePopupComponent,
    MerkezDeleteDialogComponent,
    merkezRoute,
    merkezPopupRoute,
    MerkezResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...merkezRoute,
    ...merkezPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MerkezComponent,
        MerkezDetailComponent,
        MerkezDialogComponent,
        MerkezDeleteDialogComponent,
        MerkezPopupComponent,
        MerkezDeletePopupComponent,
    ],
    entryComponents: [
        MerkezComponent,
        MerkezDialogComponent,
        MerkezPopupComponent,
        MerkezDeleteDialogComponent,
        MerkezDeletePopupComponent,
    ],
    providers: [
        MerkezService,
        MerkezPopupService,
        MerkezResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketMerkezModule {}
