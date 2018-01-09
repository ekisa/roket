package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.IsyeriDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Isyeri and its DTO IsyeriDTO.
 */
@Mapper(componentModel = "spring", uses = {MerkezMapper.class, AdresMapper.class, GPSLokasyonMapper.class, MusteriMapper.class})
public interface IsyeriMapper extends EntityMapper<IsyeriDTO, Isyeri> {

    @Mapping(source = "merkez.id", target = "merkezId")
    @Mapping(source = "adres.id", target = "adresId")
    @Mapping(source = "gpsLokasyon.id", target = "gpsLokasyonId")
    @Mapping(source = "musteri.id", target = "musteriId")
    IsyeriDTO toDto(Isyeri isyeri);

    @Mapping(source = "merkezId", target = "merkez")
    @Mapping(source = "adresId", target = "adres")
    @Mapping(source = "gpsLokasyonId", target = "gpsLokasyon")
    @Mapping(target = "emirlers", ignore = true)
    @Mapping(source = "musteriId", target = "musteri")
    Isyeri toEntity(IsyeriDTO isyeriDTO);

    default Isyeri fromId(Long id) {
        if (id == null) {
            return null;
        }
        Isyeri isyeri = new Isyeri();
        isyeri.setId(id);
        return isyeri;
    }
}
