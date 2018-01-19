import { BaseEntity } from './../../shared';

export class Isyeri implements BaseEntity {
    constructor(
        public id?: number,
        public adi?: string,
        public telefon?: string,
        public aciklama?: string,
        public emirlers?: BaseEntity[],
        public merkezId?: number,
        public merkezAdi?: string,
        public gpsLokasyonId?: number,
        // public adresId?: number,
        public adres?: string,
        public musteriId?: number,
        public musteriUnvan?: string,
        public userId?: number,
        public username?: string,
    ) {
    }
}
