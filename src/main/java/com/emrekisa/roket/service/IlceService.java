package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.IlceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Ilce.
 */
public interface IlceService {

    /**
     * Save a ilce.
     *
     * @param ilceDTO the entity to save
     * @return the persisted entity
     */
    IlceDTO save(IlceDTO ilceDTO);

    /**
     * Get all the ilces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IlceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ilce.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IlceDTO findOne(Long id);

    /**
     * Delete the "id" ilce.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
