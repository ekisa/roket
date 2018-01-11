import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Fatura } from './fatura.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FaturaService {

    private resourceUrl =  SERVER_API_URL + 'api/faturas';

    constructor(private http: Http) { }

    create(fatura: Fatura): Observable<Fatura> {
        const copy = this.convert(fatura);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(fatura: Fatura): Observable<Fatura> {
        const copy = this.convert(fatura);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Fatura> {
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
     * Convert a returned JSON object to Fatura.
     */
    private convertItemFromServer(json: any): Fatura {
        const entity: Fatura = Object.assign(new Fatura(), json);
        return entity;
    }

    /**
     * Convert a Fatura to a JSON which can be sent to the server.
     */
    private convert(fatura: Fatura): Fatura {
        const copy: Fatura = Object.assign({}, fatura);
        return copy;
    }
}
