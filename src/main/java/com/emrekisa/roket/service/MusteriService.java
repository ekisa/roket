package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.MusteriDTO;
import java.util.List;

/**
 * Service Interface for managing Musteri.
 */
public interface MusteriService {

    /**
     * Save a musteri.
     *
     * @param musteriDTO the entity to save
     * @return the persisted entity
     */
    MusteriDTO save(MusteriDTO musteriDTO);

    /**
     * Get all the musteris.
     *
     * @return the list of entities
     */
    List<MusteriDTO> findAll();

    /**
     * Get the "id" musteri.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MusteriDTO findOne(Long id);

    /**
     * Delete the "id" musteri.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
