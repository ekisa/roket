import { BaseEntity } from './../../shared';

export class Sinif implements BaseEntity {
    constructor(
        public id?: number,
        public sinifAdi?: string,
        public ogrencilers?: BaseEntity[],
    ) {
    }
}
