package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.FaturaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Fatura.
 */
public interface FaturaService {

    /**
     * Save a fatura.
     *
     * @param faturaDTO the entity to save
     * @return the persisted entity
     */
    FaturaDTO save(FaturaDTO faturaDTO);

    /**
     * Get all the faturas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FaturaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fatura.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FaturaDTO findOne(Long id);

    /**
     * Delete the "id" fatura.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
