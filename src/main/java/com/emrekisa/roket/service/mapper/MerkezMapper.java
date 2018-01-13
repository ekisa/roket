package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.MerkezDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Merkez and its DTO MerkezDTO.
 */
@Mapper(componentModel = "spring", uses = {MahalleMapper.class, GPSLokasyonMapper.class, AdresMapper.class})
public interface MerkezMapper extends EntityMapper<MerkezDTO, Merkez> {

    @Mapping(source = "mahalle.id", target = "mahalleId")
    @Mapping(source = "mahalle.adi", target = "mahalleAdi")
    @Mapping(source = "gpsLokasyon.id", target = "gpsLokasyonId")
    @Mapping(source = "adres.id", target = "adresId")
    @Mapping(source = "adres.acikAdres", target = "acikAdres")
    MerkezDTO toDto(Merkez merkez);

    @Mapping(source = "mahalleId", target = "mahalle")
//    @Mapping(source = "mahalleAdi", target = "mahalle.adi")
    @Mapping(source = "gpsLokasyonId", target = "gpsLokasyon")
    @Mapping(source = "adresId", target = "adres")
    //@Mapping(source = "acikAdres", target = "adres.acikAdres")
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
