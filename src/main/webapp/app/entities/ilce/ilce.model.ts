import { BaseEntity } from './../../shared';

export class Ilce implements BaseEntity {
    constructor(
        public id?: number,
        public ad?: string,
        public ilId?: number,
        public ilAd?: string,
    ) {
    }
}
