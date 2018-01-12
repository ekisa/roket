package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.IlceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ilce and its DTO IlceDTO.
 */
@Mapper(componentModel = "spring", uses = {IlMapper.class})
public interface IlceMapper extends EntityMapper<IlceDTO, Ilce> {

    @Mapping(source = "il.id", target = "ilId")
    IlceDTO toDto(Ilce ilce);

    @Mapping(source = "ilId", target = "il")
    Ilce toEntity(IlceDTO ilceDTO);

    default Ilce fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ilce ilce = new Ilce();
        ilce.setId(id);
        return ilce;
    }
}
