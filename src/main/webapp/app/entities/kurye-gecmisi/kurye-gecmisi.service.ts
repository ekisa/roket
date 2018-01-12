import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { KuryeGecmisi } from './kurye-gecmisi.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KuryeGecmisiService {

    private resourceUrl =  SERVER_API_URL + 'api/kurye-gecmisis';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(kuryeGecmisi: KuryeGecmisi): Observable<KuryeGecmisi> {
        const copy = this.convert(kuryeGecmisi);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(kuryeGecmisi: KuryeGecmisi): Observable<KuryeGecmisi> {
        const copy = this.convert(kuryeGecmisi);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<KuryeGecmisi> {
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
     * Convert a returned JSON object to KuryeGecmisi.
     */
    private convertItemFromServer(json: any): KuryeGecmisi {
        const entity: KuryeGecmisi = Object.assign(new KuryeGecmisi(), json);
        entity.zaman = this.dateUtils
            .convertDateTimeFromServer(json.zaman);
        return entity;
    }

    /**
     * Convert a KuryeGecmisi to a JSON which can be sent to the server.
     */
    private convert(kuryeGecmisi: KuryeGecmisi): KuryeGecmisi {
        const copy: KuryeGecmisi = Object.assign({}, kuryeGecmisi);

        copy.zaman = this.dateUtils.toDate(kuryeGecmisi.zaman);
        return copy;
    }
}
