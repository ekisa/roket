import { BaseEntity } from './../../shared';

export class Ogrenci implements BaseEntity {
    constructor(
        public id?: number,
        public adi?: string,
        public siniflaris?: BaseEntity[],
    ) {
    }
}
