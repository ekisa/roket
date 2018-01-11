import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Emir } from './emir.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EmirService {

    private resourceUrl =  SERVER_API_URL + 'api/emirs';

    constructor(private http: Http) { }

    create(emir: Emir): Observable<Emir> {
        const copy = this.convert(emir);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(emir: Emir): Observable<Emir> {
        const copy = this.convert(emir);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Emir> {
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
     * Convert a returned JSON object to Emir.
     */
    private convertItemFromServer(json: any): Emir {
        const entity: Emir = Object.assign(new Emir(), json);
        return entity;
    }

    /**
     * Convert a Emir to a JSON which can be sent to the server.
     */
    private convert(emir: Emir): Emir {
        const copy: Emir = Object.assign({}, emir);
        return copy;
    }
}
