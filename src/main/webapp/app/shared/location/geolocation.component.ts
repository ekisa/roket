import { BrowserModule } from '@angular/platform-browser';
import {NgModule, Component, OnInit, AfterViewInit, Input} from '@angular/core';

import { AgmCoreModule } from '@agm/core';
import {GeolocationService} from './geolocation.service';

@Component({
    selector: 'geo-location',
    styles: [`
    agm-map {
      height: 300px;
    }
  `],
    template: `
    <div *ngIf="geolocation; else elseBlock">
        <agm-map [latitude]="geolocation.coords.latitude" [longitude]="geolocation.coords.longitude" [zoom]=zoomValue disableDefaultUI="true">
            <agm-marker [latitude]="geolocation.coords.latitude" [longitude]="geolocation.coords.longitude">
                <agm-info-window isOpen="true">{{markerText}}</agm-info-window>
            </agm-marker>
        </agm-map>
    </div>
    <ng-template #elseBlock><span style="color:red;font-weight:bold" jhiTranslate="{{message}}">HATA</span></ng-template>
  `
})
export class GeolocationComponent implements OnInit{

    @Input()
    markerText: string;
    geolocation: Position;
    zoomValue = 15;
    message : string;
    constructor(
        private geolocationService : GeolocationService
    ) {

    }

    ngOnInit() {
        this.geolocationService.getCurrentPosition()
            .subscribe((position : Position)=>{
                    this.geolocation= position;
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
                });
    }

    // ngAfterContentInit(){
    //     navigator.geolocation.clearWatch(id)
    // }
}


