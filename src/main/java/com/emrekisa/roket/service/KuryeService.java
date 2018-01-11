package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.KuryeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Kurye.
 */
public interface KuryeService {

    /**
     * Save a kurye.
     *
     * @param kuryeDTO the entity to save
     * @return the persisted entity
     */
    KuryeDTO save(KuryeDTO kuryeDTO);

    /**
     * Get all the kuryes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KuryeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" kurye.
     *
     * @param id the id of the entity
     * @return the entity
     */
    KuryeDTO findOne(Long id);

    /**
     * Delete the "id" kurye.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
