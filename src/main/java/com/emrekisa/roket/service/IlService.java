package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.IlDTO;
import java.util.List;

/**
 * Service Interface for managing Il.
 */
public interface IlService {

    /**
     * Save a il.
     *
     * @param ilDTO the entity to save
     * @return the persisted entity
     */
    IlDTO save(IlDTO ilDTO);

    /**
     * Get all the ils.
     *
     * @return the list of entities
     */
    List<IlDTO> findAll();

    /**
     * Get the "id" il.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IlDTO findOne(Long id);

    /**
     * Delete the "id" il.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
