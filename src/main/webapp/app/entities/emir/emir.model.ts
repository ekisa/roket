import { BaseEntity } from './../../shared';

export const enum EMIR_STATU {
    'HAZIR',
    'DAGITIMDA',
    'TESLIM_EDILDI',
    'IPTAL',
    'CEZA'
}

export class Emir implements BaseEntity {
    constructor(
        public id?: number,
        public statu?: EMIR_STATU,
        public isyeriId?: number,
        public adresId?: number,
        public gpsLokasyonId?: number,
        public emirGecmisis?: BaseEntity[],
        public faturaId?: number,
    ) {
    }
}
