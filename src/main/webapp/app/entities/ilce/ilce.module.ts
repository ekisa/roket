import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RoketSharedModule } from '../../shared';
import {
    IlceService,
    IlcePopupService,
    IlceComponent,
    IlceDetailComponent,
    IlceDialogComponent,
    IlcePopupComponent,
    IlceDeletePopupComponent,
    IlceDeleteDialogComponent,
    ilceRoute,
    ilcePopupRoute,
} from './';

const ENTITY_STATES = [
    ...ilceRoute,
    ...ilcePopupRoute,
];

@NgModule({
    imports: [
        RoketSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IlceComponent,
        IlceDetailComponent,
        IlceDialogComponent,
        IlceDeleteDialogComponent,
        IlcePopupComponent,
        IlceDeletePopupComponent,
    ],
    entryComponents: [
        IlceComponent,
        IlceDialogComponent,
        IlcePopupComponent,
        IlceDeleteDialogComponent,
        IlceDeletePopupComponent,
    ],
    providers: [
        IlceService,
        IlcePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketIlceModule {}
