import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Merkez } from './merkez.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MerkezService {

    private resourceUrl =  SERVER_API_URL + 'api/merkezs';

    constructor(private http: Http) { }

    create(merkez: Merkez): Observable<Merkez> {
        const copy = this.convert(merkez);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(merkez: Merkez): Observable<Merkez> {
        const copy = this.convert(merkez);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Merkez> {
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
     * Convert a returned JSON object to Merkez.
     */
    private convertItemFromServer(json: any): Merkez {
        const entity: Merkez = Object.assign(new Merkez(), json);
        return entity;
    }

    /**
     * Convert a Merkez to a JSON which can be sent to the server.
     */
    private convert(merkez: Merkez): Merkez {
        const copy: Merkez = Object.assign({}, merkez);
        return copy;
    }
}
