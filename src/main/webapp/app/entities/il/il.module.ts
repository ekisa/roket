import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    IlService,
    IlPopupService,
    IlComponent,
    IlDetailComponent,
    IlDialogComponent,
    IlPopupComponent,
    IlDeletePopupComponent,
    IlDeleteDialogComponent,
    ilRoute,
    ilPopupRoute,
    IlResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ilRoute,
    ...ilPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IlComponent,
        IlDetailComponent,
        IlDialogComponent,
        IlDeleteDialogComponent,
        IlPopupComponent,
        IlDeletePopupComponent,
    ],
    entryComponents: [
        IlComponent,
        IlDialogComponent,
        IlPopupComponent,
        IlDeleteDialogComponent,
        IlDeletePopupComponent,
    ],
    providers: [
        IlService,
        IlPopupService,
        IlResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketIlModule {}
