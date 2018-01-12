package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.OgrenciDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ogrenci and its DTO OgrenciDTO.
 */
@Mapper(componentModel = "spring", uses = {SinifMapper.class})
public interface OgrenciMapper extends EntityMapper<OgrenciDTO, Ogrenci> {



    default Ogrenci fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ogrenci ogrenci = new Ogrenci();
        ogrenci.setId(id);
        return ogrenci;
    }
}
