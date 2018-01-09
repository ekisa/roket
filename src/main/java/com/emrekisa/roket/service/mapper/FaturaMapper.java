package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.FaturaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fatura and its DTO FaturaDTO.
 */
@Mapper(componentModel = "spring", uses = {MusteriMapper.class})
public interface FaturaMapper extends EntityMapper<FaturaDTO, Fatura> {

    @Mapping(source = "musteri.id", target = "musteriId")
    FaturaDTO toDto(Fatura fatura);

    @Mapping(source = "musteriId", target = "musteri")
    @Mapping(target = "emirlers", ignore = true)
    Fatura toEntity(FaturaDTO faturaDTO);

    default Fatura fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fatura fatura = new Fatura();
        fatura.setId(id);
        return fatura;
    }
}
