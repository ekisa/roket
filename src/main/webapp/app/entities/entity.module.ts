import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RoketMerkezModule } from './merkez/merkez.module';
import { RoketKuryeModule } from './kurye/kurye.module';
import { RoketAdresModule } from './adres/adres.module';
import { RoketIlceModule } from './ilce/ilce.module';
import { RoketIlModule } from './il/il.module';
import { RoketMahalleModule } from './mahalle/mahalle.module';
import { RoketGPSLokasyonModule } from './gps-lokasyon/gps-lokasyon.module';
import { RoketIsyeriModule } from './isyeri/isyeri.module';
import { RoketMusteriModule } from './musteri/musteri.module';
import { RoketIsciModule } from './isci/isci.module';
import { RoketMotorModule } from './motor/motor.module';
import { RoketEmirModule } from './emir/emir.module';
import { RoketFaturaModule } from './fatura/fatura.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        RoketMerkezModule,
        RoketKuryeModule,
        RoketAdresModule,
        RoketIlceModule,
        RoketIlModule,
        RoketMahalleModule,
        RoketGPSLokasyonModule,
        RoketIsyeriModule,
        RoketMusteriModule,
        RoketIsciModule,
        RoketMotorModule,
        RoketEmirModule,
        RoketFaturaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RoketEntityModule {}
