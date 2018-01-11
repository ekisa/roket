package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.IlDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Il and its DTO IlDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IlMapper extends EntityMapper<IlDTO, Il> {



    default Il fromId(Long id) {
        if (id == null) {
            return null;
        }
        Il il = new Il();
        il.setId(id);
        return il;
    }
}
