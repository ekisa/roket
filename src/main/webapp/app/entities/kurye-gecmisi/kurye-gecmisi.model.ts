import { BaseEntity } from './../../shared';

export const enum KURYE_STATU {
    'HAZIR',
    'CALISMIYOR',
    'DAGITIMA_CIKTI',
    'TESLIM_ALINIYOR',
    'DAGITIMDA',
    'MERKEZE_DONUYOR'
}

export class KuryeGecmisi implements BaseEntity {
    constructor(
        public id?: number,
        public zaman?: any,
        public statu?: KURYE_STATU,
        public kuryeId?: number,
    ) {
    }
}
