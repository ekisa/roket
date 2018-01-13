import { BaseEntity } from './../../shared';

export const enum EMIR_STATU {
    'HAZIR',
    'ATANDI',
    'DAGITIMDA',
    'TESLIM_EDILDI',
    'IPTAL',
    'CEZA'
}

export const enum BOYUT {
    'BIR',
    'IKI',
    'UC',
    'DORT',
    'BES'
}

export class Emir implements BaseEntity {
    constructor(
        public id?: number,
        public statu?: EMIR_STATU,
        public boyut?: BOYUT,
        public isyeriId?: number,
        public isyeriAdi?: string,
        public emirGecmisis?: BaseEntity[],
        public adresId?: number,
        public gpsLokasyonId?: number,
        public faturaId?: number,
    ) {
    }
}
