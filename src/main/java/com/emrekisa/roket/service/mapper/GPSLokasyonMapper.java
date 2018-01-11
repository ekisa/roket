package com.emrekisa.roket.service.mapper;

import com.emrekisa.roket.domain.*;
import com.emrekisa.roket.service.dto.GPSLokasyonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GPSLokasyon and its DTO GPSLokasyonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GPSLokasyonMapper extends EntityMapper<GPSLokasyonDTO, GPSLokasyon> {



    default GPSLokasyon fromId(Long id) {
        if (id == null) {
            return null;
        }
        GPSLokasyon gPSLokasyon = new GPSLokasyon();
        gPSLokasyon.setId(id);
        return gPSLokasyon;
    }
}
