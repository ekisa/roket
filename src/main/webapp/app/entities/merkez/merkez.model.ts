import { BaseEntity } from './../../shared';

export class Merkez implements BaseEntity {
    constructor(
        public id?: number,
        public adi?: string,
        public adresId?: number,
        public gpsLokasyonId?: number,
        public mahallelerId?: number,
    ) {
    }
}
