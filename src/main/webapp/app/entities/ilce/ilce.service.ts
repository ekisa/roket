import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Ilce } from './ilce.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IlceService {

    private resourceUrl =  SERVER_API_URL + 'api/ilces';

    constructor(private http: Http) { }

    create(ilce: Ilce): Observable<Ilce> {
        const copy = this.convert(ilce);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ilce: Ilce): Observable<Ilce> {
        const copy = this.convert(ilce);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Ilce> {
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
     * Convert a returned JSON object to Ilce.
     */
    private convertItemFromServer(json: any): Ilce {
        const entity: Ilce = Object.assign(new Ilce(), json);
        return entity;
    }

    /**
     * Convert a Ilce to a JSON which can be sent to the server.
     */
    private convert(ilce: Ilce): Ilce {
        const copy: Ilce = Object.assign({}, ilce);
        return copy;
    }
}
