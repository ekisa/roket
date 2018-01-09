package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.IsciDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Isci.
 */
public interface IsciService {

    /**
     * Save a isci.
     *
     * @param isciDTO the entity to save
     * @return the persisted entity
     */
    IsciDTO save(IsciDTO isciDTO);

    /**
     * Get all the iscis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IsciDTO> findAll(Pageable pageable);

    /**
     * Get the "id" isci.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IsciDTO findOne(Long id);

    /**
     * Delete the "id" isci.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
