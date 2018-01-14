import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    RoketSharedLibsModule,
    RoketSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    JhiLoginModalComponent,
    Principal,
    JhiTrackerService,
    HasAnyAuthorityDirective,
    JhiSocialComponent,
    SocialService,
} from './';
import {GeolocationComponent} from './location/geolocation.component';
import {GeolocationService} from "./location/geolocation.service";

@NgModule({
    imports: [
        RoketSharedLibsModule,
        RoketSharedCommonModule
    ],
    declarations: [
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        GeolocationComponent
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        JhiTrackerService,
        AuthServerProvider,
        SocialService,
        UserService,
        DatePipe,
        GeolocationService
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        RoketSharedCommonModule,
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe,
        GeolocationComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class RoketSharedModule {}
