import { BaseEntity } from './../../shared';

export class Isyeri implements BaseEntity {
    constructor(
        public id?: number,
        public adi?: string,
        public telefon?: string,
        public aciklama?: string,
        public emirlers?: BaseEntity[],
        public merkezId?: number,
        public gpsLokasyonId?: number,
        public adresId?: number,
        public musteriId?: number,
        public userId?: number,
        public username?: string,
    ) {
    }
}
