import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Ogrenci } from './ogrenci.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OgrenciService {

    private resourceUrl =  SERVER_API_URL + 'api/ogrencis';

    constructor(private http: Http) { }

    create(ogrenci: Ogrenci): Observable<Ogrenci> {
        const copy = this.convert(ogrenci);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ogrenci: Ogrenci): Observable<Ogrenci> {
        const copy = this.convert(ogrenci);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Ogrenci> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Ogrenci.
     */
    private convertItemFromServer(json: any): Ogrenci {
        const entity: Ogrenci = Object.assign(new Ogrenci(), json);
        return entity;
    }

    /**
     * Convert a Ogrenci to a JSON which can be sent to the server.
     */
    private convert(ogrenci: Ogrenci): Ogrenci {
        const copy: Ogrenci = Object.assign({}, ogrenci);
        return copy;
    }
}
