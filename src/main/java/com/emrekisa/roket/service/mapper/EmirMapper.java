package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.EmirDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Emir and its DTO EmirDTO.
 */
@Mapper(componentModel = "spring", uses = {IsyeriMapper.class, GPSLokasyonMapper.class, FaturaMapper.class, KuryeMapper.class})
public interface EmirMapper extends EntityMapper<EmirDTO, Emir> {

    @Mapping(source = "isyeri.id", target = "isyeriId")
    @Mapping(source = "isyeri.adi", target = "isyeriAdi")
    @Mapping(source = "gpsLokasyon.id", target = "gpsLokasyonId")
    @Mapping(source = "fatura.id", target = "faturaId")
    @Mapping(source = "kurye.id", target = "kuryeId")
    EmirDTO toDto(Emir emir);

    @Mapping(source = "isyeriId", target = "isyeri")
    @Mapping(target = "emirGecmisis", ignore = true)
    @Mapping(source = "gpsLokasyonId", target = "gpsLokasyon")
    @Mapping(source = "kuryeId", target = "kurye")
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
