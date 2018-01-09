package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.KuryeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Kurye and its DTO KuryeDTO.
 */
@Mapper(componentModel = "spring", uses = {MerkezMapper.class, IsciMapper.class, GPSLokasyonMapper.class})
public interface KuryeMapper extends EntityMapper<KuryeDTO, Kurye> {

    @Mapping(source = "merkez.id", target = "merkezId")
    @Mapping(source = "isci.id", target = "isciId")
    @Mapping(source = "gpsLokasyon.id", target = "gpsLokasyonId")
    KuryeDTO toDto(Kurye kurye);

    @Mapping(source = "merkezId", target = "merkez")
    @Mapping(source = "isciId", target = "isci")
    @Mapping(source = "gpsLokasyonId", target = "gpsLokasyon")
    @Mapping(target = "statuGecmisis", ignore = true)
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
