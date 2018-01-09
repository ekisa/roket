import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Adres } from './adres.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AdresService {

    private resourceUrl =  SERVER_API_URL + 'api/adres';

    constructor(private http: Http) { }

    create(adres: Adres): Observable<Adres> {
        const copy = this.convert(adres);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(adres: Adres): Observable<Adres> {
        const copy = this.convert(adres);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Adres> {
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
     * Convert a returned JSON object to Adres.
     */
    private convertItemFromServer(json: any): Adres {
        const entity: Adres = Object.assign(new Adres(), json);
        return entity;
    }

    /**
     * Convert a Adres to a JSON which can be sent to the server.
     */
    private convert(adres: Adres): Adres {
        const copy: Adres = Object.assign({}, adres);
        return copy;
    }
}
