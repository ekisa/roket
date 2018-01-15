import {GeolocationService} from './geolocation.service';
import {GeolocationComponent} from './geolocation.component';
import {AgmCoreModule} from '@agm/core';
import {BrowserModule} from '@angular/platform-browser';
import {RoketSharedModule} from '../shared.module';
import { NgModule } from '@angular/core';
@NgModule({
    imports: [
        BrowserModule,RoketSharedModule,
        AgmCoreModule.forRoot({
            apiKey: 'API_KEY'
        })
    ],
    declarations: [ GeolocationComponent ],
    providers: [GeolocationService],
    exports: [GeolocationComponent]
})
export class GeolocationModule {}
