package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.SinifDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sinif and its DTO SinifDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SinifMapper extends EntityMapper<SinifDTO, Sinif> {


    @Mapping(target = "ogrencilers", ignore = true)
    Sinif toEntity(SinifDTO sinifDTO);

    default Sinif fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sinif sinif = new Sinif();
        sinif.setId(id);
        return sinif;
    }
}
