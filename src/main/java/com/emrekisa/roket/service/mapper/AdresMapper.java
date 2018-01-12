package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.AdresDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Adres and its DTO AdresDTO.
 */
@Mapper(componentModel = "spring", uses = {MahalleMapper.class})
public interface AdresMapper extends EntityMapper<AdresDTO, Adres> {

    @Mapping(source = "mahalle.id", target = "mahalleId")
    AdresDTO toDto(Adres adres);

    @Mapping(source = "mahalleId", target = "mahalle")
    Adres toEntity(AdresDTO adresDTO);

    default Adres fromId(Long id) {
        if (id == null) {
            return null;
        }
        Adres adres = new Adres();
        adres.setId(id);
        return adres;
    }
}
