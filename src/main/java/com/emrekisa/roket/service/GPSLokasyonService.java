package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.GPSLokasyonDTO;
import java.util.List;

/**
 * Service Interface for managing GPSLokasyon.
 */
public interface GPSLokasyonService {

    /**
     * Save a gPSLokasyon.
     *
     * @param gPSLokasyonDTO the entity to save
     * @return the persisted entity
     */
    GPSLokasyonDTO save(GPSLokasyonDTO gPSLokasyonDTO);

    /**
     * Get all the gPSLokasyons.
     *
     * @return the list of entities
     */
    List<GPSLokasyonDTO> findAll();

    /**
     * Get the "id" gPSLokasyon.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GPSLokasyonDTO findOne(Long id);

    /**
     * Delete the "id" gPSLokasyon.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
