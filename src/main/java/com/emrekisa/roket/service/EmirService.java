package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.EmirDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Emir.
 */
public interface EmirService {

    /**
     * Save a emir.
     *
     * @param emirDTO the entity to save
     * @return the persisted entity
     */
    EmirDTO save(EmirDTO emirDTO);

    /**
     * Get all the emirs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmirDTO> findAll(Pageable pageable);

    /**
     * Get the "id" emir.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmirDTO findOne(Long id);

    /**
     * Delete the "id" emir.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Page<EmirDTO> findAllByIsyeriId(Pageable pageable, Long id);

    Page<EmirDTO> findAllByKuryeId(Pageable pageable, Long id);
}
