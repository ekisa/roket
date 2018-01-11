package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.EmirGecmisiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmirGecmisi and its DTO EmirGecmisiDTO.
 */
@Mapper(componentModel = "spring", uses = {EmirMapper.class})
public interface EmirGecmisiMapper extends EntityMapper<EmirGecmisiDTO, EmirGecmisi> {

    @Mapping(source = "emir.id", target = "emirId")
    EmirGecmisiDTO toDto(EmirGecmisi emirGecmisi);

    @Mapping(source = "emirId", target = "emir")
    EmirGecmisi toEntity(EmirGecmisiDTO emirGecmisiDTO);

    default EmirGecmisi fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmirGecmisi emirGecmisi = new EmirGecmisi();
        emirGecmisi.setId(id);
        return emirGecmisi;
    }
}
