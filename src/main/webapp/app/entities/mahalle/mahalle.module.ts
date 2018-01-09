import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    MahalleService,
    MahallePopupService,
    MahalleComponent,
    MahalleDetailComponent,
    MahalleDialogComponent,
    MahallePopupComponent,
    MahalleDeletePopupComponent,
    MahalleDeleteDialogComponent,
    mahalleRoute,
    mahallePopupRoute,
} from './';

const ENTITY_STATES = [
    ...mahalleRoute,
    ...mahallePopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MahalleComponent,
        MahalleDetailComponent,
        MahalleDialogComponent,
        MahalleDeleteDialogComponent,
        MahallePopupComponent,
        MahalleDeletePopupComponent,
    ],
    entryComponents: [
        MahalleComponent,
        MahalleDialogComponent,
        MahallePopupComponent,
        MahalleDeleteDialogComponent,
        MahalleDeletePopupComponent,
    ],
    providers: [
        MahalleService,
        MahallePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketMahalleModule {}
