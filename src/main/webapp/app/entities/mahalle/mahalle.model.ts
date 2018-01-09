import { BaseEntity } from './../../shared';

export class Mahalle implements BaseEntity {
    constructor(
        public id?: number,
        public mahalleAd?: string,
        public postaKodu?: string,
        public semt?: string,
        public ilceId?: number,
    ) {
    }
}
