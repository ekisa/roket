import { BaseEntity } from './../../shared';

export class Merkez implements BaseEntity {
    constructor(
        public id?: number,
        public adi?: string,
        public mahalleId?: number,
        public mahalleAdi?: string,
        public gpsLokasyonId?: number,
        // public adresId?: number,
        public adres?: string,
    ) {
    }
}
