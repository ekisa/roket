import { BaseEntity } from './../../shared';

export const enum KURYE_STATU {
    'HAZIR',
    'CALISMIYOR',
    'DAGITIMA_CIKTI',
    'TESLIM_ALINIYOR',
    'DAGITIMDA',
    'MERKEZE_DONUYOR'
}

export class Kurye implements BaseEntity {
    constructor(
        public id?: number,
        public statu?: KURYE_STATU,
        public merkezId?: number,
        public isciId?: number,
        public gpsLokasyonId?: number,
        public statuGecmisis?: BaseEntity[],
    ) {
    }
}
