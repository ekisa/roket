package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.MahalleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mahalle and its DTO MahalleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MahalleMapper extends EntityMapper<MahalleDTO, Mahalle> {



    default Mahalle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mahalle mahalle = new Mahalle();
        mahalle.setId(id);
        return mahalle;
    }
}
