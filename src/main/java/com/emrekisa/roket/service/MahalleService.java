package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.MahalleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MahalleDTO> findAll(Pageable pageable);

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
