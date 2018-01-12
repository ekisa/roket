import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    KuryeGecmisiService,
    KuryeGecmisiPopupService,
    KuryeGecmisiComponent,
    KuryeGecmisiDetailComponent,
    KuryeGecmisiDialogComponent,
    KuryeGecmisiPopupComponent,
    KuryeGecmisiDeletePopupComponent,
    KuryeGecmisiDeleteDialogComponent,
    kuryeGecmisiRoute,
    kuryeGecmisiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...kuryeGecmisiRoute,
    ...kuryeGecmisiPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        KuryeGecmisiComponent,
        KuryeGecmisiDetailComponent,
        KuryeGecmisiDialogComponent,
        KuryeGecmisiDeleteDialogComponent,
        KuryeGecmisiPopupComponent,
        KuryeGecmisiDeletePopupComponent,
    ],
    entryComponents: [
        KuryeGecmisiComponent,
        KuryeGecmisiDialogComponent,
        KuryeGecmisiPopupComponent,
        KuryeGecmisiDeleteDialogComponent,
        KuryeGecmisiDeletePopupComponent,
    ],
    providers: [
        KuryeGecmisiService,
        KuryeGecmisiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketKuryeGecmisiModule {}
