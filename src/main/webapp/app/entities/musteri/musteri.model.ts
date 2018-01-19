import { BaseEntity } from './../../shared';

export class Musteri implements BaseEntity {
    constructor(
        public id?: number,
        public unvan?: string,
        public eposta?: string,
        public telefon?: string,
        // public adresId?: number,
        public adres?: string,
        public isyerleris?: BaseEntity[],
        public faturalars?: BaseEntity[],
    ) {
    }
}
