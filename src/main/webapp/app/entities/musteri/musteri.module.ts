import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    MusteriService,
    MusteriPopupService,
    MusteriComponent,
    MusteriDetailComponent,
    MusteriDialogComponent,
    MusteriPopupComponent,
    MusteriDeletePopupComponent,
    MusteriDeleteDialogComponent,
    musteriRoute,
    musteriPopupRoute,
} from './';

const ENTITY_STATES = [
    ...musteriRoute,
    ...musteriPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MusteriComponent,
        MusteriDetailComponent,
        MusteriDialogComponent,
        MusteriDeleteDialogComponent,
        MusteriPopupComponent,
        MusteriDeletePopupComponent,
    ],
    entryComponents: [
        MusteriComponent,
        MusteriDialogComponent,
        MusteriPopupComponent,
        MusteriDeleteDialogComponent,
        MusteriDeletePopupComponent,
    ],
    providers: [
        MusteriService,
        MusteriPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketMusteriModule {}
