package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.KuryeGecmisiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity KuryeGecmisi and its DTO KuryeGecmisiDTO.
 */
@Mapper(componentModel = "spring", uses = {KuryeMapper.class})
public interface KuryeGecmisiMapper extends EntityMapper<KuryeGecmisiDTO, KuryeGecmisi> {

    @Mapping(source = "kurye.id", target = "kuryeId")
    KuryeGecmisiDTO toDto(KuryeGecmisi kuryeGecmisi);

    @Mapping(source = "kuryeId", target = "kurye")
    KuryeGecmisi toEntity(KuryeGecmisiDTO kuryeGecmisiDTO);

    default KuryeGecmisi fromId(Long id) {
        if (id == null) {
            return null;
        }
        KuryeGecmisi kuryeGecmisi = new KuryeGecmisi();
        kuryeGecmisi.setId(id);
        return kuryeGecmisi;
    }
}
