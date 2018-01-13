package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.IsyeriDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Isyeri and its DTO IsyeriDTO.
 */
@Mapper(componentModel = "spring", uses = {MerkezMapper.class, GPSLokasyonMapper.class, AdresMapper.class, MusteriMapper.class, UserMapper.class})
public interface IsyeriMapper extends EntityMapper<IsyeriDTO, Isyeri> {

    @Mapping(source = "merkez.id", target = "merkezId")
    @Mapping(source = "merkez.adi", target = "merkezAdi")
    @Mapping(source = "gpsLokasyon.id", target = "gpsLokasyonId")
    @Mapping(source = "adres.id", target = "adresId")
    @Mapping(source = "musteri.id", target = "musteriId")
    @Mapping(source = "musteri.unvan", target = "musteriUnvan")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "username")
    IsyeriDTO toDto(Isyeri isyeri);

    @Mapping(target = "emirlers", ignore = true)
    @Mapping(source = "merkezId", target = "merkez")
//    @Mapping(source = "merkezAdi", target = "merkez.adi")
    @Mapping(source = "gpsLokasyonId", target = "gpsLokasyon")
    @Mapping(source = "adresId", target = "adres")
    @Mapping(source = "musteriId", target = "musteri")
//    @Mapping(source = "musteriUnvan", target = "musteri.unvan")
    @Mapping(source = "userId", target = "user")
//    @Mapping(source = "username", target = "user.login")
    Isyeri toEntity(IsyeriDTO isyeriDTO);

    default Isyeri fromId(Long id) {
        if (id == null) {
            return null;
        }
        Isyeri isyeri = new Isyeri();
        isyeri.setId(id);
        return isyeri;
    }
}
