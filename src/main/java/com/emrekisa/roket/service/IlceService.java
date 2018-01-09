package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.IlceDTO;
import java.util.List;

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
     * @return the list of entities
     */
    List<IlceDTO> findAll();

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
