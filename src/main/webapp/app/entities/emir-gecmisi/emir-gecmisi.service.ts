import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EmirGecmisi } from './emir-gecmisi.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EmirGecmisiService {

    private resourceUrl =  SERVER_API_URL + 'api/emir-gecmisis';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(emirGecmisi: EmirGecmisi): Observable<EmirGecmisi> {
        const copy = this.convert(emirGecmisi);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(emirGecmisi: EmirGecmisi): Observable<EmirGecmisi> {
        const copy = this.convert(emirGecmisi);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<EmirGecmisi> {
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
     * Convert a returned JSON object to EmirGecmisi.
     */
    private convertItemFromServer(json: any): EmirGecmisi {
        const entity: EmirGecmisi = Object.assign(new EmirGecmisi(), json);
        entity.zaman = this.dateUtils
            .convertDateTimeFromServer(json.zaman);
        return entity;
    }

    /**
     * Convert a EmirGecmisi to a JSON which can be sent to the server.
     */
    private convert(emirGecmisi: EmirGecmisi): EmirGecmisi {
        const copy: EmirGecmisi = Object.assign({}, emirGecmisi);

        copy.zaman = this.dateUtils.toDate(emirGecmisi.zaman);
        return copy;
    }
}
