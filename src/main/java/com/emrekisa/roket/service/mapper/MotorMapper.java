package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.MotorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Motor and its DTO MotorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MotorMapper extends EntityMapper<MotorDTO, Motor> {



    default Motor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Motor motor = new Motor();
        motor.setId(id);
        return motor;
    }
}
