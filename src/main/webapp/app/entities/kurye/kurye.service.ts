import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Kurye } from './kurye.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KuryeService {

    private resourceUrl =  SERVER_API_URL + 'api/kuryes';

    constructor(private http: Http) { }

    create(kurye: Kurye): Observable<Kurye> {
        const copy = this.convert(kurye);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(kurye: Kurye): Observable<Kurye> {
        const copy = this.convert(kurye);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Kurye> {
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
     * Convert a returned JSON object to Kurye.
     */
    private convertItemFromServer(json: any): Kurye {
        const entity: Kurye = Object.assign(new Kurye(), json);
        return entity;
    }

    /**
     * Convert a Kurye to a JSON which can be sent to the server.
     */
    private convert(kurye: Kurye): Kurye {
        const copy: Kurye = Object.assign({}, kurye);
        return copy;
    }
}
