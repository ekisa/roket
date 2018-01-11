package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.AdresDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdresDTO> findAll(Pageable pageable);

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
