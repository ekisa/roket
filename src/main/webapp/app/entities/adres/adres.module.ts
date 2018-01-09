import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    AdresService,
    AdresPopupService,
    AdresComponent,
    AdresDetailComponent,
    AdresDialogComponent,
    AdresPopupComponent,
    AdresDeletePopupComponent,
    AdresDeleteDialogComponent,
    adresRoute,
    adresPopupRoute,
} from './';

const ENTITY_STATES = [
    ...adresRoute,
    ...adresPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AdresComponent,
        AdresDetailComponent,
        AdresDialogComponent,
        AdresDeleteDialogComponent,
        AdresPopupComponent,
        AdresDeletePopupComponent,
    ],
    entryComponents: [
        AdresComponent,
        AdresDialogComponent,
        AdresPopupComponent,
        AdresDeleteDialogComponent,
        AdresDeletePopupComponent,
    ],
    providers: [
        AdresService,
        AdresPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketAdresModule {}
