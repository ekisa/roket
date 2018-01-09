package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.KuryeGecmisiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing KuryeGecmisi.
 */
public interface KuryeGecmisiService {

    /**
     * Save a kuryeGecmisi.
     *
     * @param kuryeGecmisiDTO the entity to save
     * @return the persisted entity
     */
    KuryeGecmisiDTO save(KuryeGecmisiDTO kuryeGecmisiDTO);

    /**
     * Get all the kuryeGecmisis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KuryeGecmisiDTO> findAll(Pageable pageable);

    /**
     * Get the "id" kuryeGecmisi.
     *
     * @param id the id of the entity
     * @return the entity
     */
    KuryeGecmisiDTO findOne(Long id);

    /**
     * Delete the "id" kuryeGecmisi.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
