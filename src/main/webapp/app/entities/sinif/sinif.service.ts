import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Sinif } from './sinif.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SinifService {

    private resourceUrl =  SERVER_API_URL + 'api/sinifs';

    constructor(private http: Http) { }

    create(sinif: Sinif): Observable<Sinif> {
        const copy = this.convert(sinif);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(sinif: Sinif): Observable<Sinif> {
        const copy = this.convert(sinif);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Sinif> {
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
     * Convert a returned JSON object to Sinif.
     */
    private convertItemFromServer(json: any): Sinif {
        const entity: Sinif = Object.assign(new Sinif(), json);
        return entity;
    }

    /**
     * Convert a Sinif to a JSON which can be sent to the server.
     */
    private convert(sinif: Sinif): Sinif {
        const copy: Sinif = Object.assign({}, sinif);
        return copy;
    }
}
