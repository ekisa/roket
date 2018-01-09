package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.AdresDTO;
import java.util.List;

/**
 * Service Interface for managing Adres.
 */
public interface AdresService {

    /**
     * Save a adres.
     *
     * @param adresDTO the entity to save
     * @return the persisted entity
     */
    AdresDTO save(AdresDTO adresDTO);

    /**
     * Get all the adres.
     *
     * @return the list of entities
     */
    List<AdresDTO> findAll();

    /**
     * Get the "id" adres.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdresDTO findOne(Long id);

    /**
     * Delete the "id" adres.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
