package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.MusteriDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Musteri and its DTO MusteriDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MusteriMapper extends EntityMapper<MusteriDTO, Musteri> {


    @Mapping(target = "isyerleris", ignore = true)
    @Mapping(target = "adres", ignore = true)
    @Mapping(target = "faturalars", ignore = true)
    Musteri toEntity(MusteriDTO musteriDTO);

    default Musteri fromId(Long id) {
        if (id == null) {
            return null;
        }
        Musteri musteri = new Musteri();
        musteri.setId(id);
        return musteri;
    }
}
