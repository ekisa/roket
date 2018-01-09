import { BaseEntity } from './../../shared';

export class Adres implements BaseEntity {
    constructor(
        public id?: number,
        public bbk?: string,
        public ickapiNo?: string,
        public diskapiNo?: string,
        public site?: string,
        public sokak?: string,
        public cadde?: string,
        public semt?: string,
        public adresTarifi?: string,
        public mahalleId?: number,
        public ilceId?: number,
    ) {
    }
}
