import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    KuryeService,
    KuryePopupService,
    KuryeComponent,
    KuryeDetailComponent,
    KuryeDialogComponent,
    KuryePopupComponent,
    KuryeDeletePopupComponent,
    KuryeDeleteDialogComponent,
    kuryeRoute,
    kuryePopupRoute,
    KuryeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...kuryeRoute,
    ...kuryePopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        KuryeComponent,
        KuryeDetailComponent,
        KuryeDialogComponent,
        KuryeDeleteDialogComponent,
        KuryePopupComponent,
        KuryeDeletePopupComponent,
    ],
    entryComponents: [
        KuryeComponent,
        KuryeDialogComponent,
        KuryePopupComponent,
        KuryeDeleteDialogComponent,
        KuryeDeletePopupComponent,
    ],
    providers: [
        KuryeService,
        KuryePopupService,
        KuryeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketKuryeModule {}
