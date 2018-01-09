import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Isyeri } from './isyeri.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IsyeriService {

    private resourceUrl =  SERVER_API_URL + 'api/isyeris';

    constructor(private http: Http) { }

    create(isyeri: Isyeri): Observable<Isyeri> {
        const copy = this.convert(isyeri);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(isyeri: Isyeri): Observable<Isyeri> {
        const copy = this.convert(isyeri);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Isyeri> {
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
     * Convert a returned JSON object to Isyeri.
     */
    private convertItemFromServer(json: any): Isyeri {
        const entity: Isyeri = Object.assign(new Isyeri(), json);
        return entity;
    }

    /**
     * Convert a Isyeri to a JSON which can be sent to the server.
     */
    private convert(isyeri: Isyeri): Isyeri {
        const copy: Isyeri = Object.assign({}, isyeri);
        return copy;
    }
}
