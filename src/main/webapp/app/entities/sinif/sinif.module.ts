import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    SinifService,
    SinifPopupService,
    SinifComponent,
    SinifDetailComponent,
    SinifDialogComponent,
    SinifPopupComponent,
    SinifDeletePopupComponent,
    SinifDeleteDialogComponent,
    sinifRoute,
    sinifPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sinifRoute,
    ...sinifPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SinifComponent,
        SinifDetailComponent,
        SinifDialogComponent,
        SinifDeleteDialogComponent,
        SinifPopupComponent,
        SinifDeletePopupComponent,
    ],
    entryComponents: [
        SinifComponent,
        SinifDialogComponent,
        SinifPopupComponent,
        SinifDeleteDialogComponent,
        SinifDeletePopupComponent,
    ],
    providers: [
        SinifService,
        SinifPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketSinifModule {}
