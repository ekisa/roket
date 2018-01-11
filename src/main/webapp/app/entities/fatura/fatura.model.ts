import { BaseEntity } from './../../shared';

export class Fatura implements BaseEntity {
    constructor(
        public id?: number,
        public yil?: string,
        public ay?: string,
        public musteriId?: number,
        public emirlers?: BaseEntity[],
    ) {
    }
}
