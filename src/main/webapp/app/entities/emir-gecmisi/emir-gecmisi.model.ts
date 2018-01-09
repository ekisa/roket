import { BaseEntity } from './../../shared';

export const enum EMIR_STATU {
    'HAZIR',
    'DAGITIMDA',
    'TESLIM_EDILDI',
    'IPTAL',
    'CEZA'
}

export class EmirGecmisi implements BaseEntity {
    constructor(
        public id?: number,
        public zaman?: any,
        public statu?: EMIR_STATU,
        public emirId?: number,
    ) {
    }
}
