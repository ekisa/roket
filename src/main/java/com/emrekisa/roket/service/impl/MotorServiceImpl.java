package com.emrekisa.roket.service.impl;

import com.emrekisa.roket.service.MotorService;
import com.emrekisa.roket.domain.Motor;
import com.emrekisa.roket.repository.MotorRepository;
import com.emrekisa.roket.service.dto.MotorDTO;
import com.emrekisa.roket.service.mapper.MotorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Motor.
 */
@Service
@Transactional
public class MotorServiceImpl implements MotorService {

    private final Logger log = LoggerFactory.getLogger(MotorServiceImpl.class);

    private final MotorRepository motorRepository;

    private final MotorMapper motorMapper;

    public MotorServiceImpl(MotorRepository motorRepository, MotorMapper motorMapper) {
        this.motorRepository = motorRepository;
        this.motorMapper = motorMapper;
    }

    /**
     * Save a motor.
     *
     * @param motorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MotorDTO save(MotorDTO motorDTO) {
        log.debug("Request to save Motor : {}", motorDTO);
        Motor motor = motorMapper.toEntity(motorDTO);
        motor = motorRepository.save(motor);
        return motorMapper.toDto(motor);
    }

    /**
     * Get all the motors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MotorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Motors");
        return motorRepository.findAll(pageable)
            .map(motorMapper::toDto);
    }

    /**
     * Get one motor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MotorDTO findOne(Long id) {
        log.debug("Request to get Motor : {}", id);
        Motor motor = motorRepository.findOne(id);
        return motorMapper.toDto(motor);
    }

    /**
     * Delete the motor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Motor : {}", id);
        motorRepository.delete(id);
    }
}
