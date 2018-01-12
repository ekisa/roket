import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    EmirService,
    EmirPopupService,
    EmirComponent,
    EmirDetailComponent,
    EmirDialogComponent,
    EmirPopupComponent,
    EmirDeletePopupComponent,
    EmirDeleteDialogComponent,
    emirRoute,
    emirPopupRoute,
} from './';

const ENTITY_STATES = [
    ...emirRoute,
    ...emirPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmirComponent,
        EmirDetailComponent,
        EmirDialogComponent,
        EmirDeleteDialogComponent,
        EmirPopupComponent,
        EmirDeletePopupComponent,
    ],
    entryComponents: [
        EmirComponent,
        EmirDialogComponent,
        EmirPopupComponent,
        EmirDeleteDialogComponent,
        EmirDeletePopupComponent,
    ],
    providers: [
        EmirService,
        EmirPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketEmirModule {}
