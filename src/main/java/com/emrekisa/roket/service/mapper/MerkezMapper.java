package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.MerkezDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Merkez and its DTO MerkezDTO.
 */
@Mapper(componentModel = "spring", uses = {AdresMapper.class, GPSLokasyonMapper.class, MahalleMapper.class})
public interface MerkezMapper extends EntityMapper<MerkezDTO, Merkez> {

    @Mapping(source = "adres.id", target = "adresId")
    @Mapping(source = "gpsLokasyon.id", target = "gpsLokasyonId")
    @Mapping(source = "mahalleler.id", target = "mahallelerId")
    MerkezDTO toDto(Merkez merkez);

    @Mapping(source = "adresId", target = "adres")
    @Mapping(source = "gpsLokasyonId", target = "gpsLokasyon")
    @Mapping(source = "mahallelerId", target = "mahalleler")
    Merkez toEntity(MerkezDTO merkezDTO);

    default Merkez fromId(Long id) {
        if (id == null) {
            return null;
        }
        Merkez merkez = new Merkez();
        merkez.setId(id);
        return merkez;
    }
}
