package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.EmirGecmisiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EmirGecmisi.
 */
public interface EmirGecmisiService {

    /**
     * Save a emirGecmisi.
     *
     * @param emirGecmisiDTO the entity to save
     * @return the persisted entity
     */
    EmirGecmisiDTO save(EmirGecmisiDTO emirGecmisiDTO);

    /**
     * Get all the emirGecmisis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmirGecmisiDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emirGecmisi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmirGecmisiDTO findOne(Long id);

    /**
     * Delete the "id" emirGecmisi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
