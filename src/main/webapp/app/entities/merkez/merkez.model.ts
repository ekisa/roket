import { BaseEntity } from './../../shared';

export class Merkez implements BaseEntity {
    constructor(
        public id?: number,
        public adi?: string,
        public mahalleId?: number,
        public mahalleAd?: string,
        public gpsLokasyonId?: number,
        public adresId?: number,
        public acikAdres?: string,
    ) {
    }
}
