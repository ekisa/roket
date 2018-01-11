import { BaseEntity } from './../../shared';

export class GPSLokasyon implements BaseEntity {
    constructor(
        public id?: number,
        public enlem?: string,
        public boylam?: string,
    ) {
    }
}
