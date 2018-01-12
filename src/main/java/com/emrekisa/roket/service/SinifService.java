package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.SinifDTO;
import java.util.List;

/**
 * Service Interface for managing Sinif.
 */
public interface SinifService {

    /**
     * Save a sinif.
     *
     * @param sinifDTO the entity to save
     * @return the persisted entity
     */
    SinifDTO save(SinifDTO sinifDTO);

    /**
     * Get all the sinifs.
     *
     * @return the list of entities
     */
    List<SinifDTO> findAll();

    /**
     * Get the "id" sinif.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SinifDTO findOne(Long id);

    /**
     * Delete the "id" sinif.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
