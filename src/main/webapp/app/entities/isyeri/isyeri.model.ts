import { BaseEntity } from './../../shared';

export class Isyeri implements BaseEntity {
    constructor(
        public id?: number,
        public adi?: string,
        public telefon?: string,
        public aciklama?: string,
        public merkezId?: number,
        public adresId?: number,
        public gpsLokasyonId?: number,
        public emirlers?: BaseEntity[],
        public musteriId?: number,
    ) {
    }
}
