import { Component, OnInit} from '@angular/core';
import {GeolocationService} from './geolocation.service';
@Component({
    selector: 'geolocation',
     template: `<div *ngIf="geolocation; else elseBlock">
                    <a href="https://maps.googleapis.com/maps/api/staticmap?center='
                        +{{geolocation.coords.latitude}}{{geolocation.coords.longitude}}+'&zoom=14&size=400x300&key=API_KEY">
                        {{geolocation.coords.latitude}}, {{geolocation.coords.longitude}}
                    </a>
                </div>
                <ng-template #elseBlock><span style="color:red;font-weight:bold" jhiTranslate="{{message}}">HATA</span></ng-template>
                `
})
export class GeolocationComponent{

    public geolocation : Position;
    public message : String;
    constructor(
        private geolocationService : GeolocationService
    ) {

    }

    ngOnInit() {
        this.geolocationService.getCurrentPosition()
            .subscribe((position : Position)=>{
                console.log("GEOLOCATION ===> " + position.coords.latitude + ',' + position.coords.longitude);
                this.geolocation = position;
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
