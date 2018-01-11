import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    EmirGecmisiService,
    EmirGecmisiPopupService,
    EmirGecmisiComponent,
    EmirGecmisiDetailComponent,
    EmirGecmisiDialogComponent,
    EmirGecmisiPopupComponent,
    EmirGecmisiDeletePopupComponent,
    EmirGecmisiDeleteDialogComponent,
    emirGecmisiRoute,
    emirGecmisiPopupRoute,
} from './';

const ENTITY_STATES = [
    ...emirGecmisiRoute,
    ...emirGecmisiPopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmirGecmisiComponent,
        EmirGecmisiDetailComponent,
        EmirGecmisiDialogComponent,
        EmirGecmisiDeleteDialogComponent,
        EmirGecmisiPopupComponent,
        EmirGecmisiDeletePopupComponent,
    ],
    entryComponents: [
        EmirGecmisiComponent,
        EmirGecmisiDialogComponent,
        EmirGecmisiPopupComponent,
        EmirGecmisiDeleteDialogComponent,
        EmirGecmisiDeletePopupComponent,
    ],
    providers: [
        EmirGecmisiService,
        EmirGecmisiPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketEmirGecmisiModule {}
