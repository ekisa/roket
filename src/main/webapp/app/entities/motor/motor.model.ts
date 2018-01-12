import { BaseEntity } from './../../shared';

export class Motor implements BaseEntity {
    constructor(
        public id?: number,
        public numarasi?: string,
        public marka?: string,
        public model?: string,
        public yil?: string,
    ) {
    }
}
