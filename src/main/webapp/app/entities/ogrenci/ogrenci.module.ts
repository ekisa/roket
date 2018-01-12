import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    OgrenciService,
    OgrenciPopupService,
    OgrenciComponent,
    OgrenciDetailComponent,
    OgrenciDialogComponent,
    OgrenciPopupComponent,
    OgrenciDeletePopupComponent,
    OgrenciDeleteDialogComponent,
    ogrenciRoute,
    ogrenciPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ogrenciRoute,
    ...ogrenciPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OgrenciComponent,
        OgrenciDetailComponent,
        OgrenciDialogComponent,
        OgrenciDeleteDialogComponent,
        OgrenciPopupComponent,
        OgrenciDeletePopupComponent,
    ],
    entryComponents: [
        OgrenciComponent,
        OgrenciDialogComponent,
        OgrenciPopupComponent,
        OgrenciDeleteDialogComponent,
        OgrenciDeletePopupComponent,
    ],
    providers: [
        OgrenciService,
        OgrenciPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketOgrenciModule {}
