import { BaseEntity } from './../../shared';

export class Isci implements BaseEntity {
    constructor(
        public id?: number,
        public adi?: string,
        public soyadi?: string,
        public eposta?: string,
        public telefon?: string,
        public teminatTutari?: number,
        public maas?: number,
        public sicil?: string,
        public tckn?: string,
        public zimmetliMallar?: string,
        public motorId?: number,
        public motorAciklama?: string,
        public userId?: number,
        public login?: string,
    ) {
    }
}
