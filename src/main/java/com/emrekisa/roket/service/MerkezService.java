package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.MerkezDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Merkez.
 */
public interface MerkezService {

    /**
     * Save a merkez.
     *
     * @param merkezDTO the entity to save
     * @return the persisted entity
     */
    MerkezDTO save(MerkezDTO merkezDTO);

    /**
     * Get all the merkezs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MerkezDTO> findAll(Pageable pageable);

    /**
     * Get the "id" merkez.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MerkezDTO findOne(Long id);

    /**
     * Delete the "id" merkez.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
