import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Il } from './il.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IlService {

    private resourceUrl =  SERVER_API_URL + 'api/ils';

    constructor(private http: Http) { }

    create(il: Il): Observable<Il> {
        const copy = this.convert(il);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(il: Il): Observable<Il> {
        const copy = this.convert(il);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Il> {
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
     * Convert a returned JSON object to Il.
     */
    private convertItemFromServer(json: any): Il {
        const entity: Il = Object.assign(new Il(), json);
        return entity;
    }

    /**
     * Convert a Il to a JSON which can be sent to the server.
     */
    private convert(il: Il): Il {
        const copy: Il = Object.assign({}, il);
        return copy;
    }
}
