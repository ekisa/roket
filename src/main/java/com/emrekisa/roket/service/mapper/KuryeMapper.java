package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.KuryeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Kurye and its DTO KuryeDTO.
 */
@Mapper(componentModel = "spring", uses = {IsciMapper.class, MerkezMapper.class, GPSLokasyonMapper.class})
public interface KuryeMapper extends EntityMapper<KuryeDTO, Kurye> {

    @Mapping(source = "isci.id", target = "isciId")
    @Mapping(source = "merkez.id", target = "merkezId")
    @Mapping(source = "gpsLokasyon.id", target = "gpsLokasyonId")
    KuryeDTO toDto(Kurye kurye);

    @Mapping(source = "isciId", target = "isci")
    @Mapping(target = "statuGecmisis", ignore = true)
    @Mapping(source = "merkezId", target = "merkez")
    @Mapping(source = "gpsLokasyonId", target = "gpsLokasyon")
    Kurye toEntity(KuryeDTO kuryeDTO);

    default Kurye fromId(Long id) {
        if (id == null) {
            return null;
        }
        Kurye kurye = new Kurye();
        kurye.setId(id);
        return kurye;
    }
}
