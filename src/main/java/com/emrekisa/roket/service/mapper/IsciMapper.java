package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.IsciDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Isci and its DTO IsciDTO.
 */
@Mapper(componentModel = "spring", uses = {MotorMapper.class, UserMapper.class})
public interface IsciMapper extends EntityMapper<IsciDTO, Isci> {

    @Mapping(source = "motor.id", target = "motorId")
    @Mapping(source = "motor.motorAciklama", target = "motorAciklama")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "login")
    IsciDTO toDto(Isci isci);

    @Mapping(source = "motorId", target = "motor")
    @Mapping(source = "userId", target = "user")
    Isci toEntity(IsciDTO isciDTO);

    default Isci fromId(Long id) {
        if (id == null) {
            return null;
        }
        Isci isci = new Isci();
        isci.setId(id);
        return isci;
    }
}
