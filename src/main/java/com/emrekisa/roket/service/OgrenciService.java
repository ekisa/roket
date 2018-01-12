package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.OgrenciDTO;
import java.util.List;

/**
 * Service Interface for managing Ogrenci.
 */
public interface OgrenciService {

    /**
     * Save a ogrenci.
     *
     * @param ogrenciDTO the entity to save
     * @return the persisted entity
     */
    OgrenciDTO save(OgrenciDTO ogrenciDTO);

    /**
     * Get all the ogrencis.
     *
     * @return the list of entities
     */
    List<OgrenciDTO> findAll();

    /**
     * Get the "id" ogrenci.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OgrenciDTO findOne(Long id);

    /**
     * Delete the "id" ogrenci.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
