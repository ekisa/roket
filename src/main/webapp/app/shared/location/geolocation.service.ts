import {Injectable} from '@angular/core';
import { Observer } from 'rxjs/Observer';
import { Observable } from 'rxjs/Observable';

/**
 * GeolocationService class.
 * https://developers.google.com/maps/documentation/javascript/
 * https://dev.w3.org/geo/api/spec-source.html
 */
@Injectable() export class GeolocationService {

    /**
     * Tries HTML5 geolocation.
     *
     * Wraps the Geolocation API into an observable.
     *
     * @return An observable of Position
     */
    getCurrentPosition(): Observable<Position> {
        const geoOptions = {
            //maximumAge: 5 * 60 * 1000,
            timeout: 10 * 1000
            //enableHighAccuracy: true
        };
        return Observable.create((observer: Observer<Position>) => {
            // Invokes getCurrentPosition method of Geolocation API.
            navigator.geolocation.getCurrentPosition(
                (position: Position) => {
                    observer.next(position);
                    observer.complete();
                },
                (error: PositionError) => {
                    console.log('Geolocation service: ' + error.message);
                    observer.error(error);
                },
                geoOptions
            );
        });
        //watchPosition sürekli yer değişimi tespiti için kullan
        //call clearWatch to turnoff
        //https://developers.google.com/web/fundamentals/native-hardware/user-location/
    }

}
