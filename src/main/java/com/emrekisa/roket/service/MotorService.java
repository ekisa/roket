package com.emrekisa.roket.service;

import com.emrekisa.roket.service.dto.MotorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Motor.
 */
public interface MotorService {

    /**
     * Save a motor.
     *
     * @param motorDTO the entity to save
     * @return the persisted entity
     */
    MotorDTO save(MotorDTO motorDTO);

    /**
     * Get all the motors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MotorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" motor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MotorDTO findOne(Long id);

    /**
     * Delete the "id" motor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
