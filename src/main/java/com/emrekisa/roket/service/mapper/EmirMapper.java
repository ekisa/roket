package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.EmirDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Emir and its DTO EmirDTO.
 */
@Mapper(componentModel = "spring", uses = {IsyeriMapper.class, AdresMapper.class, GPSLokasyonMapper.class, FaturaMapper.class})
public interface EmirMapper extends EntityMapper<EmirDTO, Emir> {

    @Mapping(source = "isyeri.id", target = "isyeriId")
    @Mapping(source = "adres.id", target = "adresId")
    @Mapping(source = "gpsLokasyon.id", target = "gpsLokasyonId")
    @Mapping(source = "fatura.id", target = "faturaId")
    EmirDTO toDto(Emir emir);

    @Mapping(source = "isyeriId", target = "isyeri")
    @Mapping(target = "emirGecmisis", ignore = true)
    @Mapping(source = "adresId", target = "adres")
    @Mapping(source = "gpsLokasyonId", target = "gpsLokasyon")
    @Mapping(source = "faturaId", target = "fatura")
    Emir toEntity(EmirDTO emirDTO);

    default Emir fromId(Long id) {
        if (id == null) {
            return null;
        }
        Emir emir = new Emir();
        emir.setId(id);
        return emir;
    }
}