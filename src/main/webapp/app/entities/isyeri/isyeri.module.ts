import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    IsyeriService,
    IsyeriPopupService,
    IsyeriComponent,
    IsyeriDetailComponent,
    IsyeriDialogComponent,
    IsyeriPopupComponent,
    IsyeriDeletePopupComponent,
    IsyeriDeleteDialogComponent,
    isyeriRoute,
    isyeriPopupRoute,
    IsyeriResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...isyeriRoute,
    ...isyeriPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IsyeriComponent,
        IsyeriDetailComponent,
        IsyeriDialogComponent,
        IsyeriDeleteDialogComponent,
        IsyeriPopupComponent,
        IsyeriDeletePopupComponent,
    ],
    entryComponents: [
        IsyeriComponent,
        IsyeriDialogComponent,
        IsyeriPopupComponent,
        IsyeriDeleteDialogComponent,
        IsyeriDeletePopupComponent,
    ],
    providers: [
        IsyeriService,
        IsyeriPopupService,
        IsyeriResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketIsyeriModule {}
