import { BrowserModule } from '@angular/platform-browser';
import {NgModule, Component, OnInit} from '@angular/core';

import { AgmCoreModule } from '@agm/core';
import {GeolocationService} from "./geolocation.service";

@Component({
    selector: 'geo-location',
    styles: [`
    agm-map {
      height: 300px;
    }
  `],
    template: `
    <div *ngIf="geolocation; else elseBlock">
        <agm-map [latitude]="geolocation.latitude" [longitude]="geolocation.longitude" [zoom]=zoomValue>
            <agm-marker [latitude]="geolocation.latitude" [longitude]="geolocation.longitude">
                <agm-info-window>Paketiniz Burada</agm-info-window>
            </agm-marker>
        </agm-map>
    </div>
    <ng-template #elseBlock><span style="color:red;font-weight:bold" jhiTranslate="{{message}}">HATA</span></ng-template>
  `
})
export class GeolocationComponent implements OnInit{
    geolocation: Coordinates;
    zoomValue = 15;
    message : string;
    constructor(
        private geolocationService : GeolocationService
    ) {

    }

    ngOnInit() {
        this.geolocationService.getCurrentPosition()
            .subscribe((position : Position)=>{
                    this.geolocation= position.coords;
                },(error: PositionError) => {
                    if (error.code > 0) {
                        switch (error.code) {
                            case error.PERMISSION_DENIED:
                                this.message = 'error.location.permissionDenied';
                                break;
                            case error.POSITION_UNAVAILABLE:
                                this.message = 'error.location.positionUnavailable';
                                break;
                            case error.TIMEOUT:
                                this.message = 'error.location.timeout';
                                break;
                        }
                    }
                },
                () => console.log('Geolocation service: completed.'));
    }
}


