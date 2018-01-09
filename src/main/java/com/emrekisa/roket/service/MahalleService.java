package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.MahalleDTO;
import java.util.List;

/**
 * Service Interface for managing Mahalle.
 */
public interface MahalleService {

    /**
     * Save a mahalle.
     *
     * @param mahalleDTO the entity to save
     * @return the persisted entity
     */
    MahalleDTO save(MahalleDTO mahalleDTO);

    /**
     * Get all the mahalles.
     *
     * @return the list of entities
     */
    List<MahalleDTO> findAll();

    /**
     * Get the "id" mahalle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MahalleDTO findOne(Long id);

    /**
     * Delete the "id" mahalle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
