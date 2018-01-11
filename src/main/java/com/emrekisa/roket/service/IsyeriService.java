package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.IsyeriDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Isyeri.
 */
public interface IsyeriService {

    /**
     * Save a isyeri.
     *
     * @param isyeriDTO the entity to save
     * @return the persisted entity
     */
    IsyeriDTO save(IsyeriDTO isyeriDTO);

    /**
     * Get all the isyeris.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IsyeriDTO> findAll(Pageable pageable);

    /**
     * Get the "id" isyeri.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IsyeriDTO findOne(Long id);

    /**
     * Delete the "id" isyeri.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
